package by.kuropatin.dkr.config;

import by.kuropatin.dkr.util.CacheNames;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    @Value("${cache.config.initial-capacity}")
    private int initialCapacity;

    @Value("${cache.config.maximum-size}")
    private int maximumSize;

    @Value("${cache.config.expiration-time-in-seconds}")
    private int expirationTimeInSeconds;

    @Bean
    public CacheManager cacheManager() {
        final CaffeineCacheManager cacheManager = new CaffeineCacheManager(CacheNames.getCacheNames());
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }

    private Caffeine<Object, Object> cacheProperties() {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterAccess(expirationTimeInSeconds, TimeUnit.SECONDS)
                .weakKeys()
                .recordStats();
    }
}