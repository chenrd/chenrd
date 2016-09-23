/*
 * 文件名：ScheduleJob.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年1月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.quartz;

import com.chenrd.quartz.parameter.TaskParameter;

public class ScheduleJob
{
    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";
    public static final String CONCURRENT_IS = "1";
    public static final String CONCURRENT_NOT = "0";
    
    private String jobName;
    
    private String jobGroup;
    
    private String description;
    
    private String jobStatus;
    
    private String cronExpression;
    
    private TaskHandle task;
    
    private TaskParameter parameter;
    
    
    /**
     * 
     */
    public ScheduleJob()
    {
        super();
    }
    
    
    /**
     * 
     * @param jobName
     * @param jobGroup
     */
    public ScheduleJob(String jobName, String jobGroup)
    {
        super();
        this.jobName = jobName;
        this.jobGroup = jobGroup;
    }



    /**
     * 
     * @param jobName
     * @param jobGroup
     * @param cronExpression
     */
    public ScheduleJob(String jobName, String jobGroup, TaskParameter parameter)
    {
        super();
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.parameter = parameter;
    }
    
    
    /**
     * 
     * @param jobName
     * @param jobGroup
     * @param cronExpression
     * @param targetClass
     */
    public ScheduleJob(String jobName, String jobGroup, String cronExpression, TaskHandle task, TaskParameter parameter)
    {
        super();
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.cronExpression = cronExpression;
        this.task = task;
        this.parameter = parameter;
    }

    /**
     * @return Returns the jobName.
     */
    public String getJobName()
    {
        return jobName;
    }

    /**
     * @param jobName The jobName to set.
     */
    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    /**
     * @return Returns the jobGroup.
     */
    public String getJobGroup()
    {
        return jobGroup;
    }

    /**
     * @param jobGroup The jobGroup to set.
     */
    public void setJobGroup(String jobGroup)
    {
        this.jobGroup = jobGroup;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return Returns the jobStatus.
     */
    public String getJobStatus()
    {
        return jobStatus;
    }

    /**
     * @param jobStatus The jobStatus to set.
     */
    public void setJobStatus(String jobStatus)
    {
        this.jobStatus = jobStatus;
    }

    /**
     * @return Returns the cronExpression.
     */
    public String getCronExpression()
    {
        return cronExpression;
    }

    /**
     * @param cronExpression The cronExpression to set.
     */
    public void setCronExpression(String cronExpression)
    {
        this.cronExpression = cronExpression;
    }

    /**
     * @return Returns the task.
     */
    public TaskHandle getTask()
    {
        return task;
    }

    /**
     * @param task The task to set.
     */
    public void setTask(TaskHandle task)
    {
        this.task = task;
    }

    /**
     * @return Returns the parameter.
     */
    public TaskParameter getParameter()
    {
        return parameter;
    }

    /**
     * @param parameter The parameter to set.
     */
    public void setParameter(TaskParameter parameter)
    {
        this.parameter = parameter;
    }

}
