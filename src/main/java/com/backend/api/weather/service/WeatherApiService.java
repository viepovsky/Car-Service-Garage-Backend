package com.backend.api.weather.service;

import com.backend.api.weather.client.WeatherApiClient;
import com.backend.api.weather.domain.ForecastDto;
import com.backend.api.weather.domain.LocationDto;
import com.backend.api.weather.domain.StoredForecast;
import com.backend.api.weather.mapper.ForecastMapper;
import com.backend.api.weather.repository.StoredForecastRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiService.class);
    private final WeatherApiClient weatherApiClient;
    private final StoredForecastRepository storedForecastRepository;
    private final ForecastMapper forecastMapper;

    public void getAndStore14DaysForecast(String city) {
        if (storedForecastRepository.findAllByCity(city).size() != 0) {
            storedForecastRepository.deleteAllByCity(city);
        }
        List<LocationDto> locationDtoList = weatherApiClient.getIdForCityName(city);
        int cityId = locationDtoList.get(0).getLocations().get(0).getCityId();
        List<ForecastDto> forecastDtoList = weatherApiClient.get14DaysForecast(cityId);
        List<StoredForecast> storedForecastList = forecastMapper.mapToStoredForecastList(forecastDtoList, city);
        storedForecastRepository.saveAll(storedForecastList);
        LOGGER.info("Stored 14 days forecast for city: " + city);
    }
}
