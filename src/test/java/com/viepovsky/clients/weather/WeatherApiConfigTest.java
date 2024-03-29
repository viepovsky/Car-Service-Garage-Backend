package com.viepovsky.clients.weather;

import com.viepovsky.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class WeatherApiConfigTest {
    @Autowired
    private WeatherApiConfig config;

    @Test
    void testWeatherConfig() {
        //Given
        //When
        String endpoint = config.getWeatherApiEndpoint();
        String key = config.getWeatherApiKey();
        String host = config.getWeatherApiHost();
        //Then
        assertNotNull(endpoint);
        assertNotNull(key);
        assertNotNull(host);
    }
}