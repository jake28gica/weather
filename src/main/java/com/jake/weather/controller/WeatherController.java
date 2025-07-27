package com.jake.weather.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.jake.weather.service.WeatherService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
public class WeatherController {

    @Resource
    private Cache<String, Object> cache;

    @Resource
    private WeatherService weatherService;

    @GetMapping("getWeather/{city}")
    public ResponseEntity<Map<String, Object>> getWeather( @PathVariable("city") String city ) throws Exception {

        Map<String, Object> weather = ( Map<String, Object> ) cache.getIfPresent( city );

        if (Objects.isNull( weather ) ) {
            weather = weatherService.getWeather( city );
            cache.put( city, weather );
        }

        return ResponseEntity.ok(weather);
    }

}
