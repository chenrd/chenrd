/*
 * 文件名：Cache.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年7月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache;

public interface CacheInterface<K, V>
{
    
    void put(K k, V v);
    
}
