package com.backend.api.weather.client;

import com.backend.api.weather.config.WeatherApiConfig;
import com.backend.api.weather.domain.ForecastDto;
import com.backend.api.weather.domain.LocationDto;
import com.backend.scheduler.ApplicationScheduler;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
@AllArgsConstructor
public class WeatherApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiClient.class);
    private final RestTemplate restTemplate;
    private final WeatherApiConfig weatherApiConfig;

    public List<ForecastDto> get14DaysForecast(int cityId) {
        HttpHeaders headers = createHeader();
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        URI url = UriComponentsBuilder.fromHttpUrl(weatherApiConfig.getWeatherApiEndpoint() + "/forecast/daily/" + cityId)
                .queryParam("alt", "0")
                .queryParam("tempunit", "C")
                .queryParam("windunit", "KMH")
                .queryParam("periods", "14")
                .queryParam("dataset", "full")
                .build()
                .encode()
                .toUri();
        try {
            ResponseEntity<ForecastDto[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, ForecastDto[].class);
            return Arrays.asList(ofNullable(response.getBody()).orElse(new ForecastDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<LocationDto> getIdForCityName(String cityName) {
        HttpHeaders headers = createHeader();
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        URI url = UriComponentsBuilder.fromHttpUrl(weatherApiConfig.getWeatherApiEndpoint() + "/location/search/" + cityName)
                .queryParam("lang", "pl")
                .queryParam("country", "pl")
                .build()
                .encode()
                .toUri();
        try {
            ResponseEntity<LocationDto[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, LocationDto[].class);
            return Arrays.asList(ofNullable(response.getBody()).orElse(new LocationDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    private HttpHeaders createHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", weatherApiConfig.getWeatherApiKey());
        headers.set("X-RapidAPI-Host", weatherApiConfig.getWeatherApiHost());
        return headers;
    }
}