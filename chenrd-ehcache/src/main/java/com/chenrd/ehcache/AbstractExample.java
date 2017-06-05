/*
 * 文件名：AbstractExample.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache;

import java.util.concurrent.TimeUnit;

import org.ehcache.config.ResourceUnit;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

public abstract class AbstractExample<K, V> implements EhcacheExample<K, V> {

	/**
	 * 缓存名称
	 */
	String name;

	/**
	 * 
	 */
	Class<K> keyClass;

	Class<V> valueClass;

	/**
	 * 内存缓存 最大数量 堆内
	 */
	int heapSize;

	/**
	 * 内存缓存 最大数量 堆外
	 */
	int offheap;

	int diskSize;

	int expiry;

	/**
	 * 内存缓存最大数量单位， {@link EntryUnit 条目数} {@link MemoryUnit 文件或内存大小}
	 */
	ResourceUnit heapUnit = EntryUnit.ENTRIES;

	MemoryUnit offheapUnit = MemoryUnit.MB;

	/**
	 * 最大磁盘大小
	 */
	MemoryUnit diskUnit = MemoryUnit.MB;

	TimeUnit expiryUnit = TimeUnit.SECONDS;

	/**
	 * @param name
	 * @param keyClass
	 * @param valueClass
	 */
	public AbstractExample(String name, Class<K> keyClass, Class<V> valueClass) {
		super();
		this.name = name;
		this.keyClass = keyClass;
		this.valueClass = valueClass;
	}

	public EhcacheExample<K, V> withHeap(int heapSize, ResourceUnit unit) {
		this.heapSize = heapSize;
		this.heapUnit = unit;
		return this;
	}

	public EhcacheExample<K, V> withOffheap(int offheapSize, MemoryUnit unit) {
		this.offheap = offheapSize;
		this.offheapUnit = unit;
		return this;
	}

	public EhcacheExample<K, V> withDisk(int diskSize, MemoryUnit unit) {
		this.diskSize = diskSize;
		this.diskUnit = unit;
		return this;
	}

	public EhcacheExample<K, V> withExpiry(int expiry, TimeUnit unit) {
		this.expiry = expiry;
		this.expiryUnit = unit;
		return this;
	}
}
