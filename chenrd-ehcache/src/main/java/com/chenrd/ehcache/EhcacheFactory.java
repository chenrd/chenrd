/*
 * 文件名：EhCacheFactory.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月28日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache;

import org.ehcache.Cache;

/**
 * 单例模式的加工厂
 * 
 * @author chenrd
 * @version 2016年6月28日
 * @see EhcacheFactory
 * @since
 */
public class EhcacheFactory
{
    
    private static EhcacheFactory ehcacheFactory;
    
    private EhcacheBuilder builder;
    
    private EhcacheBuilder persistenceBuilder;
    
    private ExampleBuilder exampleBuilder;
    
    
    private EhcacheFactory()
    {
        
    }
    
    
    public static EhcacheFactory getEhcacheFactory()
    {
        if (ehcacheFactory == null) {
            ehcacheFactory = new EhcacheFactory();
            ehcacheFactory.init();
        }
        return ehcacheFactory;
    }
    
    public EhcacheFactory init()
    {
        exampleBuilder = ExampleBuilder.newExampleBuilder();
        return EhcacheFactory.getEhcacheFactory();
    }
    
    @SuppressWarnings("unchecked")
    public synchronized <K, V> Cache<K, V> newCache(EhcacheExample<K, V> example)
    {
        if (example instanceof PersistenceEhcache) 
        {
            if (persistenceBuilder == null) persistenceBuilder = EhcacheBuilder.newEhcacheBuilder();
            return (Cache<K, V>) persistenceBuilder.withCache(example,  
                    ((PersistenceEhcache<K, V>) example).storagePath, EhcacheBuilder.newCache(example)).build();
        }
        else
        {
            if (builder == null) builder = EhcacheBuilder.newEhcacheBuilder();
            return (Cache<K, V>) builder.withCache(example,  
                null, EhcacheBuilder.newCache(example)).build();
        }
    }
    
    
    public Cache<?, ?> get(QueryExampleParameter parameter)
    {
        if (!exampleBuilder.containsKey(parameter.getName())) return null;
        if (exampleBuilder.isPersistenceCacheExample(parameter.getName()))
            return builder.getEhcache(parameter.getName(), parameter.getKeyClass(), parameter.getValueClass());
        else
            return persistenceBuilder.getEhcache(parameter.getName(), parameter.getKeyClass(), parameter.getValueClass());
    }

    /**
     * @return Returns the builder.
     */
    public EhcacheBuilder getBuilder()
    {
        return builder;
    }

    /**
     * @param builder The builder to set.
     */
    public void setBuilder(EhcacheBuilder builder)
    {
        this.builder = builder;
    }

    /**
     * @return Returns the exampleBuilder.
     */
    public ExampleBuilder getExampleBuilder()
    {
        return exampleBuilder;
    }
}
