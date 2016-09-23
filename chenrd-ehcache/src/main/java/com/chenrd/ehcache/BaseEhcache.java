/*
 * 文件名：BaseEhcache.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年7月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache;

public class BaseEhcache<K, V> extends AbstractExample<K, V>
{

    public BaseEhcache(String name, Class<K> keyClass, Class<V> valueClass)
    {
        super(name, keyClass, valueClass);
    }

}
