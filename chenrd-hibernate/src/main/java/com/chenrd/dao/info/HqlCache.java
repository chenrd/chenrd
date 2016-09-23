/*
 * 文件名：HqlCache.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年7月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.info;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

import com.chenrd.ehcache.CacheInterface;
import com.chenrd.ehcache.EhcacheFactory;


public class HqlCache implements CacheInterface<String, String>
{
    
    private static EhcacheFactory cacheFactory = EhcacheFactory.getEhcacheFactory();
    
    private static final String name = "HQL_CACHE";
    
    private int hqlHeapSize = 10;
    
    private int hqlExpiry = 100;
    
    /**
     * 默认堆外的空间为10M
     */
    private int offheapSize = 10;
    
    private Cache<String, String> cache = cacheFactory.newCache(cacheFactory.getExampleBuilder()
        .newBaseCacheExample(name, String.class, String.class).withHeap(hqlHeapSize, EntryUnit.ENTRIES).withExpiry(hqlExpiry, TimeUnit.SECONDS).withOffheap(offheapSize, MemoryUnit.MB));
    
    private static CacheInterface<String, String> cacheInterface = null;
    
    public static CacheInterface<String, String> getHqlCache()
    {
         
        if (cacheInterface == null) {
            cacheInterface = new HqlCache();
            Properties properties = new Properties();
            InputStream inputStream = HqlCache.class.getResourceAsStream("/hibernate.cache.properties");
            try
            {
                properties.load(inputStream);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            } finally {
                if (inputStream != null) try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            ((HqlCache) cacheInterface).hqlHeapSize = Integer.parseInt(properties.getProperty("hql.heap.size"));
            ((HqlCache) cacheInterface).hqlHeapSize = Integer.parseInt(properties.getProperty("hql.expiry"));
        }
        return cacheInterface;
    }
    
    private HqlCache()
    {
        
    }

    @Override
    public void put(String k, String v)
    {
        cache.put(k, v);
    }
    
}
