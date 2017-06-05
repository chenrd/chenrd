/*
 * 文件名：SchedulerBean.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年3月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.executor;

import java.io.Serializable;
import java.util.Date;

public class SchedulerBean implements Serializable {

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = 6138292071494685627L;
	
	private String name;
	
	private String cron;
	
	private TaskParameter parameter;
	
	/**
	 * 扩展字段 TaskScheduler.scheduleAtFixedRate
	 */
	private Date startTime; 
	
	/**
	 * 扩展字段 
	 */
	private Long period;
	
	/**
	 * 扩展字段 
	 */
	private Long delay;

	/**
	 * 
	 */
	public SchedulerBean() {
		super();
	}

	/**
	 * @param name
	 * @param cron
	 * @param parameter
	 */
	public SchedulerBean(String name, String cron, TaskParameter parameter) {
		super();
		this.name = name;
		this.cron = cron;
		this.parameter = parameter;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the cron.
	 */
	public String getCron() {
		return cron;
	}

	/**
	 * @param cron The cron to set.
	 */
	public void setCron(String cron) {
		this.cron = cron;
	}

	/**
	 * @return Returns the parameter.
	 */
	public TaskParameter getParameter() {
		return parameter;
	}

	/**
	 * @param parameter The parameter to set.
	 */
	public void setParameter(TaskParameter parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return Returns the startTime.
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime The startTime to set.
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return Returns the period.
	 */
	public Long getPeriod() {
		return period;
	}

	/**
	 * @param period The period to set.
	 */
	public void setPeriod(Long period) {
		this.period = period;
	}

	/**
	 * @return Returns the delay.
	 */
	public Long getDelay() {
		return delay;
	}

	/**
	 * @param delay The delay to set.
	 */
	public void setDelay(Long delay) {
		this.delay = delay;
	}
	
}
