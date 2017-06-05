/*
 * 文件名：AbstractTaskParameter.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年3月22日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.executor.abs;

import com.chenrd.executor.TaskCallback;
import com.chenrd.executor.TaskParameter;

public abstract class AbstractTaskParameter implements TaskParameter {
	
	protected String username; //启动当前任务的用户 这个参数必填
	
	protected TaskCallback callback;
	
	boolean exist = false;
	
	@Override
	public void addCallback(TaskCallback callback) {
		this.callback = callback;
		this.exist = true;
	}
	
	@Override
	public void callback() {
		if (existCallback()) {
			callback.callback();
		}
	}
	
	private boolean existCallback() {
		return exist;
	}

	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}
