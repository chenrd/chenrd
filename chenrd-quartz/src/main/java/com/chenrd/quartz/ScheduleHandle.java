/*
 * 文件名：ScheduleHandle.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年1月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component("scheduleHandle")
public class ScheduleHandle
{
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    
    public void addJob(ScheduleJob job) throws SchedulerException
    {
        /*if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
            return;
        }*/
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            //Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
            
            JobDetail jobDetail = JobBuilder.newJob(BaseScheduleJob.class).withIdentity(job.getJobName(), job.getJobGroup()).build();

            jobDetail.getJobDataMap().put(BaseScheduleJob.TASK_MANAGER_KEY, job.getTask());
            jobDetail.getJobDataMap().put(BaseScheduleJob.SCHEDULE_JOB, job);
            jobDetail.getJobDataMap().put(BaseScheduleJob.PARAMS_KEY, job.getParameter());

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }
   
    
    /**
     * 获取所有计划中的任务列表  
     *   
     * @return  
     * @throws SchedulerException  
     */  
    public List<ScheduleJob> getAllJob() throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();  
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);  
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();  
        for (JobKey jobKey : jobKeys) {  
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);  
            for (Trigger trigger : triggers) {  
                ScheduleJob job = new ScheduleJob();  
                job.setJobName(jobKey.getName());  
                job.setJobGroup(jobKey.getGroup());  
                job.setDescription("触发器:" + trigger.getKey());  
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
                job.setJobStatus(triggerState.name());  
                if (trigger instanceof CronTrigger) {  
                    CronTrigger cronTrigger = (CronTrigger) trigger;  
                    String cronExpression = cronTrigger.getCronExpression();  
                    job.setCronExpression(cronExpression);  
                }  
                jobList.add(job);  
            }  
        }  
        return jobList;  
    }  
 
    /** 
     * 所有正在运行的job 
     *  
     * @return 
     * @throws SchedulerException 
     */  
    public List<ScheduleJob> getRunningJob() throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();  
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());  
        for (JobExecutionContext executingJob : executingJobs) {  
            ScheduleJob job = new ScheduleJob();  
            JobDetail jobDetail = executingJob.getJobDetail();  
            JobKey jobKey = jobDetail.getKey();  
            Trigger trigger = executingJob.getTrigger();  
            job.setJobName(jobKey.getName());  
            job.setJobGroup(jobKey.getGroup());  
            job.setDescription("触发器:" + trigger.getKey());  
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
            job.setJobStatus(triggerState.name());  
            if (trigger instanceof CronTrigger) {  
                CronTrigger cronTrigger = (CronTrigger) trigger;  
                String cronExpression = cronTrigger.getCronExpression();  
                job.setCronExpression(cronExpression);  
            }  
            jobList.add(job);  
        }  
        return jobList;  
    }  
 
    /** 
     * 暂停一个job 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
        scheduler.pauseJob(jobKey);  
    }
 
    /** 
     * 恢复一个job 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }
    
    /** 
     * 删除一个job 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }
    
    /** 
     * 立即执行job 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //注意事项：放进去参数之后，当当前任务结束之后应该删除掉这个参数 调用这个方法removeScheduleJobParams
        if (scheduleJob.getParameter() != null)
        {
            JobDetail detail = scheduler.getJobDetail(jobKey);
            JobDataMap jobDataMap = detail.getJobDataMap();
            jobDataMap.put(BaseScheduleJob.PARAMS_KEY, scheduleJob.getParameter());
            scheduler.triggerJob(jobKey, jobDataMap);
        }
        else
        {
            scheduler.triggerJob(jobKey);
        }
    }
    
    /** 
     * 更新job时间表达式 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {  
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);  
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());  
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
        scheduler.rescheduleJob(triggerKey, trigger);  
    }

    /**
     * @return Returns the schedulerFactoryBean.
     */
    public SchedulerFactoryBean getSchedulerFactoryBean()
    {
        return schedulerFactoryBean;
    }

    /**
     * @param schedulerFactoryBean The schedulerFactoryBean to set.
     */
    public void setSchedulerFactoryBean(SchedulerFactoryBean schedulerFactoryBean)
    {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }
    
}
