/*
 * 文件名：SpringTaskSchedulerService.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年3月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.executor;

import java.util.Date;

public interface SpringTaskSchedulerService {
	
	/**
	 * 调节器添加一个定时任务
	 * @param task
	 * @param schedulerBean 
	 * @see
	 */
	void schedule(Task task, SchedulerBean schedulerBean);
	
	/**
	 * 添加任务在指定的时候运行
	 * @param task
	 * @param parameter 
	 * @see
	 */
	void schedule(Task task, TaskParameter parameter, Date startTime);
	
	/**
	 * 延迟运行
	 * @param task
	 * @param delay 
	 * @see
	 */
	void scheduleWithFixedDelay(Task task, TaskParameter parameter, long delay);
	
	/**
	 * 删除一个定时任务
	 * @param scheduleName 
	 * @see
	 */
	boolean removeSchedule(String scheduleName, boolean mayInterruptIfRunning);
	
	/**
	 * 理解执行一个任务
	 * @param task
	 * @param parameter 
	 * @see
	 */
	void execute(Task task, TaskParameter parameter);
	
}
