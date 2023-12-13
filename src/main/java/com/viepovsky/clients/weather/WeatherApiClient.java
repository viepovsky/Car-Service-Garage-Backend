package com.viepovsky.clients.weather;

import com.viepovsky.clients.weather.dto.ForecastDto;
import com.viepovsky.clients.weather.dto.LocationDto;
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

import static java.util.Optional.ofNullable;

@Component
@AllArgsConstructor
class WeatherApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiClient.class);
    private final RestTemplate restTemplate;
    private final WeatherApiConfig weatherApiConfig;

    public ForecastDto get14DaysForecast(int cityId) {
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
            ResponseEntity<ForecastDto> response = restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, ForecastDto.class);
            return ofNullable(response.getBody()).orElse(new ForecastDto());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ForecastDto();
        }
    }

    public LocationDto getIdForCityName(String cityName) {
        HttpHeaders headers = createHeader();
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        URI url = UriComponentsBuilder.fromHttpUrl(weatherApiConfig.getWeatherApiEndpoint() + "/location/search/" + cityName)
                .queryParam("lang", "pl")
                .queryParam("country", "pl")
                .build()
                .encode()
                .toUri();
        try {
            ResponseEntity<LocationDto> response = restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, LocationDto.class);
            return ofNullable(response.getBody()).orElse(new LocationDto());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new LocationDto();
        }
    }

    private HttpHeaders createHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", weatherApiConfig.getWeatherApiKey());
        headers.set("X-RapidAPI-Host", weatherApiConfig.getWeatherApiHost());
        return headers;
    }
}
