/*
 * 文件名：BaseScheduleJob.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年1月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.chenrd.quartz.parameter.TaskParameter;

/**
 * 
 * @author xuwenqiang
 * @version 2016年1月8日
 * @see BaseScheduleJob
 * @since
 */
public class BaseScheduleJob implements Job
{
    
    public final static String TASK_MANAGER_KEY = "taskManager";
    
    public final static String SCHEDULE_JOB = "scheduleJob";
    
    /**
     * 当点击立即执行时，可能有需求传递参数
     */
    public final static String PARAMS_KEY = "params";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        TaskHandle task = (TaskHandle) context.getMergedJobDataMap().get(TASK_MANAGER_KEY);
        ScheduleJob job = (ScheduleJob) context.getMergedJobDataMap().get(SCHEDULE_JOB);
        task.execute(job.getJobName(), job.getJobGroup(), (TaskParameter) context.getMergedJobDataMap().get(PARAMS_KEY));
    }

}
