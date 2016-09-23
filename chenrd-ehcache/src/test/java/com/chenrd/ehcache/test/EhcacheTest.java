/*
 * 文件名：EhcacheTest.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月27日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache.test;

import java.io.File;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.junit.Test;

public class EhcacheTest
{
    @Test
    public void testName() throws Exception
    {
        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerBuilder.persistence("E:\\workFile\\work\\temp1" + File.separator + "myData")) 
            .withCache("persistent-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, Long.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(1, EntryUnit.ENTRIES)
                    .disk(10, MemoryUnit.MB, true)) 
                )
            .build(true);
        
        Cache<Long, Long> cache = persistentCacheManager.getCache("persistent-cache", Long.class, Long.class);
        for (long i = 0; i < 1000; i++)
        {
            cache.put(i, 1000000000000000L);
        }
        
        System.out.println(cache.get(1L));
        
       /* CacheConfiguration<Long, String> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
            ResourcePoolsBuilder.heap(100)) 
        .withExpiry(Expirations.timeToLiveExpiration(Duration.of(20, TimeUnit.SECONDS))) 
        .build();
        */
        persistentCacheManager.close();
    }
}
