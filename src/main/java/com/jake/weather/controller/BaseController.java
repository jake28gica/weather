package com.jake.weather.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.jake.weather.service.WeatherService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class BaseController {

    @Resource
    WeatherService weatherService;

    @ExceptionHandler( Throwable.class )
    @SuppressWarnings( "unchecked" )
    public ResponseEntity<Map<String, Object>> resolveException( Exception e, HttpServletRequest request ) {
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String city = pathVariables.get("city");

        try {
            return ResponseEntity.ok( weatherService.getFallback( city ) );
        } catch ( Exception ex ){
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( Map.of("status", "error", "reason", "City might be invalid or services are unavailable"));
        }

    }

}
