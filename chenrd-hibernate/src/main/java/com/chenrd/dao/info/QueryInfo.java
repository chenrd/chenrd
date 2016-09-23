/*
 * 文件名：QueryInfo.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.info;

import java.io.Serializable;

/**
 * 
 * @author chenrd
 * @version 2016年5月18日
 * @see QueryInfo
 * @since
 */
public interface QueryInfo extends Serializable
{
    
    Object get(String name);
}
