package com.jake.weather.service;

import java.util.Map;

public interface WeatherService {
    Map<String, Object> getWeather( String city ) throws Exception;
    Map<String, Object> getFallback( String city ) throws Exception;
}
