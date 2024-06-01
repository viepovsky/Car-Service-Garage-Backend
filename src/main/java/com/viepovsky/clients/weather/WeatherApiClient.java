package com.viepovsky.clients.weather;

import com.viepovsky.clients.weather.dto.ForecastDto;
import com.viepovsky.clients.weather.dto.LocationDto;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@AllArgsConstructor
class WeatherApiClient {
    private static final String HEADER_KEY = "X-RapidAPI-Key";
    private static final String HEADER_HOST = "X-RapidAPI-Host";
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiClient.class);
    private final WeatherApiConfig weatherApiConfig;
    private final RestClient restClient;

    @Autowired
    WeatherApiClient(WeatherApiConfig weatherApiConfig) {
        this.weatherApiConfig = weatherApiConfig;
        restClient =
                RestClient.builder()
                        .requestFactory(new HttpComponentsClientHttpRequestFactory())
                        .defaultHeaders(
                                httpHeaders -> {
                                    httpHeaders.set(
                                            HEADER_KEY, weatherApiConfig.getWeatherApiKey());
                                    httpHeaders.set(
                                            HEADER_HOST, weatherApiConfig.getWeatherApiHost());
                                })
                        .build();
    }

    public ForecastDto get14DaysForecast(int cityId) {
        URI url =
                UriComponentsBuilder.fromHttpUrl(
                                weatherApiConfig.getWeatherApiEndpoint()
                                        + "/forecast/daily/"
                                        + cityId)
                        .queryParam("alt", "0")
                        .queryParam("tempunit", "C")
                        .queryParam("windunit", "KMH")
                        .queryParam("periods", "14")
                        .queryParam("dataset", "full")
                        .build()
                        .encode()
                        .toUri();
        ResponseEntity<ForecastDto> response =
                restClient.get().uri(url).retrieve().toEntity(ForecastDto.class);
        LOGGER.info("Retrieved {} forecast", response.getBody());
        return response.getBody();
    }

    public LocationDto getIdForCityName(String cityName) {
        URI url =
                UriComponentsBuilder.fromHttpUrl(
                                weatherApiConfig.getWeatherApiEndpoint()
                                        + "/location/search/"
                                        + cityName)
                        .queryParam("lang", "pl")
                        .queryParam("country", "pl")
                        .build()
                        .encode()
                        .toUri();
        ResponseEntity<LocationDto> response =
                restClient.get().uri(url).retrieve().toEntity(LocationDto.class);
        LOGGER.info("Retrieved {} location", response.getBody());
        return response.getBody();
    }
}
