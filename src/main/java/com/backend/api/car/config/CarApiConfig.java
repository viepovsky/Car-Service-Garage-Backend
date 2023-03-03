package com.backend.api.car.config;

import org.springframework.beans.factory.annotation.Value;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CarApiConfig {
    @Value("${car.api.endpoint}")
    private String carApiEndpoint;
    @Value("${car.api.key}")
    private String carApiKey;
    @Value("${car.api.host}")
    private String carApiHost;
}
