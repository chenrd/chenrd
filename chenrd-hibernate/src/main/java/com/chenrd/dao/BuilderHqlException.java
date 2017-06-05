/*
 * 文件名：BuilderHqlException.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年3月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao;

public class BuilderHqlException extends RuntimeException {

	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
	private static final long serialVersionUID = -5098105020388707914L;

	public BuilderHqlException(String msg) {
		super(msg);
	}
	public BuilderHqlException(String msg, Exception e) {
		super(msg, e);
	}
}
