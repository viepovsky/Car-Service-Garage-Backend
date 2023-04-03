package com.backend.api.weather.config;

import com.backend.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@MockBean(ApplicationScheduler.class)
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