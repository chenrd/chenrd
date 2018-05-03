/*
 * 文件名：SpringTaskSchedulerServiceImpl.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年3月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.executor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.chenrd.executor.SchedulerBean;
import com.chenrd.executor.SpringTaskSchedulerService;
import com.chenrd.executor.Task;
import com.chenrd.executor.TaskParameter;

@SuppressWarnings("unchecked")
@Component("springTaskSchedulerService")
public class SpringTaskSchedulerServiceImpl implements SpringTaskSchedulerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpringTaskSchedulerServiceImpl.class);

	@Autowired
	private TaskScheduler scheduler;
	
	@Autowired
	private TaskExecutor executor;
	
	@Value("#{settings['scheduler.size']}")
	private Integer schedulerSize;
	
	private Map<String, ScheduledFuture<Object>> futures;
	
	@PostConstruct
	public void initSpringTaskSchedulerService() {
		if (schedulerSize == null) {
			schedulerSize = 10;
		}
		futures = new HashMap<>(schedulerSize);
	}

	@Override
	public void schedule(final Task task, final SchedulerBean schedulerBean) {
		ScheduledFuture<Object> future = (ScheduledFuture<Object>) scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					task.execute(schedulerBean.getParameter());
					if (schedulerBean.getParameter() != null){
						schedulerBean.getParameter().callback();
					}
				} catch (Exception e) {
					LOG.error("系统定时任务执行出错", e);
				}
			}
		}, new CronTrigger(schedulerBean.getCron()));
		futures.put(schedulerBean.getName(), future);
	}

	@Override
	public void schedule(final Task task, final TaskParameter parameter, Date startTime) {
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					task.execute(parameter);
					if (parameter != null) {
						parameter.callback();
					}
				} catch (Exception e) {
					LOG.error("系统定时任务执行出错", e);
				}
			}
		}, startTime);
	}

	@Override
	public void scheduleWithFixedDelay(final Task task, final TaskParameter parameter, long delay) {
		scheduler.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				try {
					task.execute(parameter);
					if (parameter != null) {
						parameter.callback();
					}
				} catch (Exception e) {
					LOG.error("立即执行任务出错", e);
				}
			}
		}, delay);
	}


	@Override
	public void execute(final Task task, final TaskParameter parameter) {
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					task.execute(parameter);
					if (parameter != null) {
						parameter.callback();
					}
				} catch (Exception e) {
					LOG.error("立即执行任务出错", e);
				}
			}
		});
	}

	@Override
	public boolean removeSchedule(String scheduleName, boolean mayInterruptIfRunning) {
		ScheduledFuture<Object> future = futures.remove(scheduleName);
		return future.cancel(mayInterruptIfRunning);
	}
	
}
