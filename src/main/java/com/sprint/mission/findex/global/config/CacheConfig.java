package com.sprint.mission.findex.global.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(CacheConfig.CaffeineProperties.class)
@RequiredArgsConstructor
@Configuration
public class CacheConfig {

    @ConfigurationProperties(prefix = "cache")
    public record CaffeineProperties(Map<String, String> specs) {}

    private final CaffeineProperties caffeineProperties;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        caffeineProperties.specs().forEach((name, spec) ->
            manager.registerCustomCache(name, Caffeine.from(spec).build()));
        return manager;
    }
}