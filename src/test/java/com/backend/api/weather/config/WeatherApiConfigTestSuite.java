package com.backend.api.weather.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WeatherApiConfigTestSuite {
    @Autowired
    private WeatherApiConfig weatherApiConfig;

    @Test
    void testWeatherConfig() {
        //Given
        //When
        String endpoint = weatherApiConfig.getWeatherApiEndpoint();
        String key = weatherApiConfig.getWeatherApiKey();
        String host = weatherApiConfig.getWeatherApiHost();
        //Then
        assertNotNull(endpoint);
        assertNotNull(key);
        assertNotNull(host);
    }
}