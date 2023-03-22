package com.backend.api.weather.client;

import com.backend.api.weather.config.WeatherApiConfig;
import com.backend.scheduler.ApplicationScheduler;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class WeatherApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiClient.class);
    private final RestTemplate restTemplate;
    private final WeatherApiConfig weatherApiConfig;
}
