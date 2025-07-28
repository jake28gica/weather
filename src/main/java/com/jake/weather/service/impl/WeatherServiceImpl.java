package com.jake.weather.service.impl;

import com.jake.weather.exception.ApiException;
import com.jake.weather.service.WeatherService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value( "${weatherStack.api-key}" )
    String WEATHER_STACK_ACCESS_TOKEN;

    @Value( "${openWeather.api-key}" )
    String OPEN_WEATHER_ACCESS_TOKEN;

    private static final String WEATHER_STACK_API_URL = "http://api.weatherstack.com/current?access_key=%s&query=%s";
    private static final String OPEN_WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    @Resource
    private RestTemplate restTemplate;

    @Override
    @SuppressWarnings( {"rawtypes" } )
    public Map<String, Object> getWeather( String city ) {

        Map result = restTemplate.getForObject( String.format( WEATHER_STACK_API_URL, WEATHER_STACK_ACCESS_TOKEN, city) , Map.class );
        Map current = ( Map ) Objects.requireNonNull( result ).get( "current");

        return Map.of("wind_speed", current.get( "wind_speed" ), "temperature_degrees", current.get( "temperature"));
    }

    @SuppressWarnings( {"rawtypes", "unchecked"} )
    public Map<String, Object> getFallback( String city ) throws ApiException {
        Map result = restTemplate.getForObject( String.format( OPEN_WEATHER_API_URL, city, OPEN_WEATHER_ACCESS_TOKEN ), Map.class );

        if ( result == null || !result.containsKey( "main" ) ) {
            throw new ApiException("fallback failed");
        }

        Map<String, Object> main = ( Map<String, Object> ) result.get( "main" );
        Map<String, Object> wind = ( Map<String, Object> ) result.get( "wind" );

        return Map.of("wind_speed", wind.get( "speed" ), "temperature_degrees", main.get( "temp" ));
    }
}
