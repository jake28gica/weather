package com.jake.weather.service.impl;

import com.jake.weather.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class WeatherServiceImplTest {

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks( this);

        weatherService.WEATHER_STACK_ACCESS_TOKEN = "dummy_weather_stack_key";
        weatherService.OPEN_WEATHER_ACCESS_TOKEN = "dummy_open_weather_key";
    }

    @Test
    void testGetWeatherSuccess() throws Exception {
        String city = "Manila";

        Map<String, Object> currentMap = Map.of(
                "wind_speed", 15,
                "temperature", 32
        );

        Map<String, Object> mockResponse = Map.of("current", currentMap);

        when(restTemplate.getForObject(
                contains("api.weatherstack.com"), eq(Map.class)))
                .thenReturn(mockResponse);

        Map<String, Object> result = weatherService.getWeather(city);

        assertEquals(15, result.get("wind_speed"));
        assertEquals(32, result.get("temperature_degrees"));
    }

    @Test
    void testGetWeatherThrowsExceptionOnNull() {
        String city = "Manila";

        when(restTemplate.getForObject(
                contains("api.weatherstack.com"), eq(Map.class)))
                .thenReturn(null);

        assertThrows(NullPointerException.class, () -> weatherService.getWeather(city));
    }

    @Test
    void testGetFallbackSuccess() throws Exception {
        String city = "Cebu";

        Map<String, Object> mainMap = Map.of("temp", 305.15);
        Map<String, Object> windMap = Map.of("speed", 5.5);

        Map<String, Object> mockResponse = Map.of(
                "main", mainMap,
                "wind", windMap
        );

        when(restTemplate.getForObject(
                contains("api.openweathermap.org"), eq(Map.class)))
                .thenReturn(mockResponse);

        Map<String, Object> result = weatherService.getFallback(city);

        assertEquals(5.5, result.get("wind_speed"));
        assertEquals(305.15, result.get("temperature_degrees"));
    }

    @Test
    void testGetFallbackThrowsException() {
        String city = "InvalidCity";

        when(restTemplate.getForObject(
                contains("api.openweathermap.org"), eq(Map.class)))
                .thenReturn(Map.of()); // No "main" key

        ApiException exception = assertThrows( ApiException.class, () -> weatherService.getFallback( city));
        assertEquals("fallback failed", exception.getMessage());
    }
}
