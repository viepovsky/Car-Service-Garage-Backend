package com.viepovsky.clients.car;


import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
class CarApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarApiClient.class);
    private final CarApiConfig carApiConfig;
    private final RestClient restClient =
            RestClient.builder()
                    .requestFactory(new HttpComponentsClientHttpRequestFactory())
                    .baseUrl(carApiConfig.getCarApiEndpoint())
                    .defaultHeaders(httpHeaders -> headersBuild())
                    .build();

    public List<CarApiDto> getCarModels(int year, String make, String type) {
        URI url =
                UriComponentsBuilder.fromHttpUrl("")
                        .queryParam("limit", 20)
                        .queryParam("page", 0)
                        .queryParam("year", year)
                        .queryParam("vehicleMake", make)
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

    private HttpHeaders headersBuild() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", carApiConfig.getCarApiKey());
        headers.set("X-RapidAPI-Host", carApiConfig.getCarApiHost());
        return headers;
    }
}
