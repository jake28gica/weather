package com.jake.weather.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffeineConfig {
    @Bean
    public Cache<String, ?> caffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite( 3, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();
    }
}
