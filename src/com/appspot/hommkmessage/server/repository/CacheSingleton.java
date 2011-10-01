package com.appspot.hommkmessage.server.repository;

import java.util.Collections;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

public enum CacheSingleton {
	INSTANCE;
	private Cache cache = null;

	CacheSingleton() {
		try {
			cache = CacheManager.getInstance().getCacheFactory()
					.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Cache getCache() {
		return cache;
	}
}
