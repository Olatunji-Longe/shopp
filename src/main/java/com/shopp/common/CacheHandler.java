package com.shopp.common;

import java.util.Set;

public interface CacheHandler {

    void evictCache(String cacheName, String cacheKey);

    void evictCache(String cacheName, Set<String> cacheKeys);

    void evictCache(String cacheName);
}
