/*
 * 文件名：Business.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.business;

import java.io.Serializable;

import com.chenrd.example.VO;

public interface Business {

	void publish(Serializable id);

	void delete(Serializable id);

	<T extends VO> T get(Serializable id, Class<T> clazz);

}
