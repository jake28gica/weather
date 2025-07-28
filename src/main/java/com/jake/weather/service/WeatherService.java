package com.jake.weather.service;

import com.jake.weather.exception.ApiException;

import java.util.Map;

public interface WeatherService {
    Map<String, Object> getWeather( String city ) throws ApiException;
    Map<String, Object> getFallback( String city ) throws ApiException;
}
