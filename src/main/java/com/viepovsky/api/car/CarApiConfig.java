package com.viepovsky.api.car;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class CarApiConfig {
    @Value("${car.api.endpoint}")
    private String carApiEndpoint;
    @Value("${car.api.key}")
    private String carApiKey;
    @Value("${car.api.host}")
    private String carApiHost;
}
