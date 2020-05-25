package com.shopp.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import com.shopp.configs.props.CacheSpecs;
import com.shopp.configs.props.CacheSpecs.CacheSetting;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheSpecs.class)
public class CaffeineCacheConfig extends CachingConfigurerSupport {

    private CacheSpecs specs;

    @Autowired
    public CaffeineCacheConfig(CacheSpecs specs){
        this.specs = specs;
    }

    @Bean
    public CacheManager cacheManager(Ticker ticker) {
        SimpleCacheManager manager = new SimpleCacheManager();
        if (specs != null) {
            manager.setCaches(
                    specs.getSettings().entrySet().stream()
                    .map(entry -> buildCache(entry.getKey(), entry.getValue(), ticker))
                    .collect(Collectors.toList())
            );
        }
        return manager;
    }

    private CaffeineCache buildCache(String cacheName, CacheSetting setting, Ticker ticker) {
        log.info("cache: {}, specs: {}", cacheName, setting.toString());
        return new CaffeineCache(cacheName,
                Caffeine.newBuilder()
                        .initialCapacity(setting.getMinCapacity().intValue())
                        .maximumSize(setting.getMaxCapacity())
                        .expireAfterWrite(setting.getTtlSecs(), TimeUnit.SECONDS)
                        .ticker(ticker)
                        .recordStats()
                        .build()
        );
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }
}
