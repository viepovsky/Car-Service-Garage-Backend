package com.viepovsky.clients.car;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
class CarApiConfig {

    @Value("${car.api.endpoint}")
    private String carApiEndpoint;

    @Value("${car.api.key}")
    private String carApiKey;

    @Value("${car.api.host}")
    private String carApiHost;
}
