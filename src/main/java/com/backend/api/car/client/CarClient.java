package com.backend.api.car.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class CarClient {
    private final RestTemplate restTemplate;
}
