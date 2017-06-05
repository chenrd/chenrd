/*
 * 文件名：AbstractTask.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年3月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.executor.abs;

import com.chenrd.executor.Task;

public abstract class AbstractTask implements Task {
	
	private String taskName;

	/**
	 * @return Returns the taskName.
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName The taskName to set.
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
