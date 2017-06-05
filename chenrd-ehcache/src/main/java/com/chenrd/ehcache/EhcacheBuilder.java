/*
 * 文件名：EhcacheBuilder.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.ehcache;

import org.ehcache.CacheManager;
import org.ehcache.config.Builder;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.Ehcache;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

public class EhcacheBuilder implements Builder<Ehcache<?, ?>> {

	private CacheManager cacheManager;

	private EhcacheExample<?, ?> cache;

	public static EhcacheBuilder newEhcacheBuilder() {
		return new EhcacheBuilder();
	}

	public static <K, V> CacheConfigurationBuilder<K, V> newCache(EhcacheExample<K, V> cache) {
		AbstractExample<K, V> abstractExample = (AbstractExample<K, V>) cache;
		ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.heap(abstractExample.heapSize);
		if (abstractExample.offheap > 0)
			resourcePoolsBuilder.offheap(abstractExample.offheap, abstractExample.offheapUnit);
		if (abstractExample.diskSize > 0)
			resourcePoolsBuilder.disk(abstractExample.diskSize, abstractExample.diskUnit, true);

		CacheConfigurationBuilder<K, V> cacheConfigurationBuilder = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(abstractExample.keyClass, abstractExample.valueClass,
						resourcePoolsBuilder);
		if (abstractExample.expiry > 0)
			cacheConfigurationBuilder.withExpiry(
					Expirations.timeToLiveExpiration(Duration.of(abstractExample.expiry, abstractExample.expiryUnit)));
		return cacheConfigurationBuilder;

	}

	public <K, V> EhcacheBuilder withCache(EhcacheExample<K, V> cache, String storagePath,
			CacheConfigurationBuilder<K, V> configurationBuilder) {
		CacheManagerBuilder<CacheManager> cacheManagerBuilder = CacheManagerBuilder.newCacheManagerBuilder();
		if (cache instanceof PersistenceEhcache)
			cacheManagerBuilder.with(CacheManagerBuilder.persistence(storagePath));
		cacheManager = cacheManagerBuilder.withCache(((AbstractExample<K, V>) cache).name, configurationBuilder)
				.build(true);
		this.cache = cache;
		return this;
	}

	public <K, V> Ehcache<K, V> getEhcache(String name, Class<K> keyClass, Class<V> valueClass) {
		return (Ehcache<K, V>) cacheManager.getCache(name, keyClass, valueClass);
	}

	@Override
	public Ehcache<?, ?> build() {
		CacheConfiguration<?, ?> configuration = cacheManager.getRuntimeConfiguration().getCacheConfigurations()
				.get(((AbstractExample<?, ?>) cache).name);
		return (Ehcache<?, ?>) cacheManager.getCache(((AbstractExample<?, ?>) cache).name, configuration.getKeyType(),
				configuration.getValueType());
	}

}
