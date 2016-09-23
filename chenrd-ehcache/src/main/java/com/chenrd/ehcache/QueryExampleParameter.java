/*
 * 文件名：QueryExampleParameter.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache;

/**
 * 获取需要这个参数
 * 
 * @author chenrd
 * @version 2016年6月30日
 * @see QueryExampleParameter
 * @since
 */
public interface QueryExampleParameter
{
    
    String getName();
    
    Class<?> getKeyClass();
    
    Class<?> getValueClass();
}
