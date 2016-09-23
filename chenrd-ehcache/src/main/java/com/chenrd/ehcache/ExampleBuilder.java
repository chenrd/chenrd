/*
 * 文件名：ExampleBuilder.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache;

import java.util.HashMap;
import java.util.Map;

public class ExampleBuilder
{
    
    private Map<String, EhcacheExample<?, ?>> examples = new HashMap<String, EhcacheExample<?, ?>>();
    
    public static ExampleBuilder newExampleBuilder()
    {
        return new ExampleBuilder();
    }
    
    public <K, V> EhcacheExample<K, V> newBaseCacheExample(String name, Class<K> keyClass, Class<V> valueClass)
    {
        BaseEhcache<K, V> ehcache = new BaseEhcache<K, V>(name, keyClass, valueClass);
        examples.put(ehcache.name, ehcache);
        return ehcache;
    }
    
    public <K, V> EhcacheExample<K, V> newPersistenceCacheExample(String name, Class<K> keyClas, Class<V> valueClass)
    {
        PersistenceEhcache<K, V> ehcache = new PersistenceEhcache<K, V>(name, keyClas, valueClass);
        examples.put(ehcache.name, ehcache);
        return ehcache;
    }
    
    public boolean isPersistenceCacheExample(String cacheName)
    {
        if (examples.get(cacheName) instanceof PersistenceEhcache)
            return true;
        return false;
    }
    
    public boolean containsKey(String name)
    {
        return examples.containsKey(name);
    }
    
    public EhcacheExample<?, ?> get(String name)
    {
        return examples.get(name);
    }
    
}
