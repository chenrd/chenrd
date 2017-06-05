/*
 * 文件名：Status.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.example;

public interface Status {
	/**
	 * 删除状态
	 */
	int DELETED = -1;

	/**
	 * 不启用状态
	 */
	int OFF = 0;

	/**
	 * 启用
	 */
	int ON = 1;
}
