/*
 * 文件名：PersistenceEhcache.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache;

public class PersistenceEhcache<K, V> extends AbstractExample<K, V>
{
    
    String storagePath;
    

    public PersistenceEhcache(String name, Class<K> keyClass, Class<V> valueClass)
    {
        super(name, keyClass, valueClass);
    }


    /**
     * @return Returns the storagePath.
     */
    public String getStoragePath()
    {
        return storagePath;
    }

    /**
     * @param storagePath The storagePath to set.
     */
    public void setStoragePath(String storagePath)
    {
        this.storagePath = storagePath;
    }
}
