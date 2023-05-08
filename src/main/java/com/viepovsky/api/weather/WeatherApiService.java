package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiService.class);
    private final WeatherApiClient client;
    private final StoredForecastRepository repository;
    private final ForecastMapper mapper;

    public void getAndStore14DaysForecast(String city) {
        if (repository.findAllByCity(city).size() != 0) {
            repository.deleteAllByCity(city);
        }
        LocationDto locationDto = client.getIdForCityName(city);
        int cityId = locationDto.getLocations().get(0).getCityId();
        ForecastDto forecastDto = client.get14DaysForecast(cityId);
        List<ForecastsDto> forecastsDtoList = forecastDto.getForecasts();
        List<StoredForecast> storedForecastList = mapper.mapToStoredForecastList(forecastsDtoList, city);
        repository.saveAll(storedForecastList);
        LOGGER.info("Stored 14 days forecast for city: " + city);
    }

    public CityForecastDto getForecastForCityAndDate(String city, LocalDate date) {
        StoredForecast storedForecast = repository.findByDateAndCity(date, city);
        CityForecastDto cityForecastDto = mapper.mapToCityForecastDto(storedForecast);
        LOGGER.info("Retrieved city forecast: " + cityForecastDto);
        return cityForecastDto;
    }
}
