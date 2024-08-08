package com.viepovsky.clients.car;

import com.viepovsky.clients.car.dto.CarApiDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Component
class CarApiClient {
    private static final String HEADER_KEY = "X-RapidAPI-Key";
    private static final String HEADER_HOST = "X-RapidAPI-Host";
    private static final Logger LOGGER = LoggerFactory.getLogger(CarApiClient.class);
    private final CarApiConfig carApiConfig;
    private final RestClient restClient;

    @Autowired
    CarApiClient(CarApiConfig carApiConfig) {
        this.carApiConfig = carApiConfig;
        restClient =
                RestClient.builder()
                        .requestFactory(new HttpComponentsClientHttpRequestFactory())
                        .defaultHeaders(
                                httpHeaders -> {
                                    httpHeaders.set(HEADER_KEY, carApiConfig.getCarApiKey());
                                    httpHeaders.set(HEADER_HOST, carApiConfig.getCarApiHost());
                                })
                        .build();
    }

    public List<CarApiDto> getCarModels(int year, String make, String type) {
        URI url =
                UriComponentsBuilder.fromHttpUrl(carApiConfig.getCarApiEndpoint())
                        .queryParam("limit", 20)
                        .queryParam("page", 0)
                        .queryParam("year", year)
                        .queryParam("make", make)
                        .queryParam("type", type)
                        .build()
                        .encode()
                        .toUri();

        ResponseEntity<List<CarApiDto>> responseEntity =
                restClient
                        .get()
                        .uri(url)
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<>() {});

        List<CarApiDto> responseBody = responseEntity.getBody();
        LOGGER.info(
                "Retrieved model list with size {}", Objects.requireNonNull(responseBody).size());
        return responseBody;
    }
}
