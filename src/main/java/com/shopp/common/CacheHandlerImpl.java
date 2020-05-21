package com.shopp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Transactional
public class CacheHandlerImpl implements CacheHandler {

    private CacheManager cacheManager;

    @Autowired
    public CacheHandlerImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void evictCache(String cacheName, String cacheKey) {
        Cache cache = cacheManager.getCache(cacheName);
        if(cache != null){
            cache.evictIfPresent(cacheKey);
        }
    }

    @Override
    public void evictCache(String cacheName, Set<String> cacheKeys) {
        Cache cache = cacheManager.getCache(cacheName);
        if(cache != null){
            for(String cacheKey : cacheKeys){
                cache.evictIfPresent(cacheKey);
            }
        }
    }

    @Override
    public void evictCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if(cache != null){
            cache.clear();
        }
    }

}
