package com.viepovsky.clients.car;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
@AllArgsConstructor
class CarApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarApiClient.class);
    private final RestTemplate restTemplate;
    private final CarApiConfig carApiConfig;

    public List<Integer> getCarYears() {
        URI url = urlBuild("years");
        HttpHeaders headers = headersBuild();
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<Integer>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, new ParameterizedTypeReference<>() {
            });
            return responseEntity.getBody();
        } catch (RestClientException e) {
            LOGGER.error("Error while getting car years. " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<String> getCarMakes() {
        URI url = urlBuild("makes");
        return sendRequest(url, "makes");
    }

    public List<String> getCarTypes() {
        URI url = urlBuild("types");
        return sendRequest(url, "types");
    }

    private List<String> sendRequest(URI url, String text) {
        HttpHeaders headers = headersBuild();
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<String>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, new ParameterizedTypeReference<>() {
            });
            return responseEntity.getBody();
        } catch (RestClientException e) {
            LOGGER.error("Error while getting car " + text + ". " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<CarApiDto> getCarModels(int year, String make, String type) {
        URI url = UriComponentsBuilder.fromHttpUrl(carApiConfig.getCarApiEndpoint())
                .queryParam("limit", 20)
                .queryParam("page", 0)
                .queryParam("year", year)
                .queryParam("make", make)
                .queryParam("type", type)
                .build()
                .encode()
                .toUri();
        HttpHeaders headers = headersBuild();
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);
        try {
            ResponseEntity<CarApiDto[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, CarApiDto[].class);
            return Arrays.asList(ofNullable(responseEntity.getBody()).orElse(new CarApiDto[0]));
        } catch (RestClientException e) {
            LOGGER.error("Error while getting car models. " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private URI urlBuild(String path) {
        return UriComponentsBuilder.fromHttpUrl(carApiConfig.getCarApiEndpoint() + "/" + path).build().encode().toUri();
    }

    private HttpHeaders headersBuild() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", carApiConfig.getCarApiKey());
        headers.set("X-RapidAPI-Host", carApiConfig.getCarApiHost());
        return headers;
    }
}
