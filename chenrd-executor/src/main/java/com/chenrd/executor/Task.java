/*
 * 文件名：Task.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年3月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.executor;

public interface Task {
	
	String execute(TaskParameter parameter);
	
	void setTaskName(String taskName);
	
	public final static String EXECUTE_SUCCESS = "success";
}
