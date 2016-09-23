/*
 * 文件名：EhcacheType.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月29日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache;

import java.util.concurrent.TimeUnit;

import org.ehcache.config.ResourceUnit;
import org.ehcache.config.units.MemoryUnit;

public interface EhcacheExample<K, V>
{
    
    EhcacheExample<K, V> withHeap(int heapSize, ResourceUnit unit);
    
    EhcacheExample<K, V> withOffheap(int offheapSize, MemoryUnit unit);
    
    EhcacheExample<K, V> withDisk(int diskSize, MemoryUnit unit);
    
    EhcacheExample<K, V> withExpiry(int expiry, TimeUnit unit);
    
}
