package com.viepovsky.clients.weather;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class WeatherApiConfig {

    @Value("${weather.api.endpoint}")
    private String weatherApiEndpoint;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${weather.api.host}")
    private String weatherApiHost;
}
