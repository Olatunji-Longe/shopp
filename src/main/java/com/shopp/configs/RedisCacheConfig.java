package com.shopp.configs;

import com.shopp.configs.props.CacheSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableCaching
@EnableConfigurationProperties({/*CacheProps.class, */CacheSpecs.class})
public class RedisCacheConfig extends CachingConfigurerSupport {

    private CacheSpecs specs;

    @Autowired
    public RedisCacheConfig(CacheSpecs specs){
        this.specs = specs;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return createCacheConfiguration(specs.getTimeoutSecs());
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        for(String cacheName : specs.getSettings().keySet()){
            cacheConfigurations.put(cacheName, createCacheConfiguration(specs.getSettings().get(cacheName).getTtlSecs()));
        }

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration())
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    private static RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(timeoutInSeconds));
    }
}
