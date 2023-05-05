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
    private final WeatherApiClient weatherApiClient;
    private final StoredForecastRepository storedForecastRepository;
    private final ForecastMapper forecastMapper;

    public void getAndStore14DaysForecast(String city) {
        if (storedForecastRepository.findAllByCity(city).size() != 0) {
            storedForecastRepository.deleteAllByCity(city);
        }
        LocationDto locationDto = weatherApiClient.getIdForCityName(city);
        int cityId = locationDto.getLocations().get(0).getCityId();
        ForecastDto forecastDto = weatherApiClient.get14DaysForecast(cityId);
        List<ForecastsDto> forecastsDtoList = forecastDto.getForecasts();
        List<StoredForecast> storedForecastList = forecastMapper.mapToStoredForecastList(forecastsDtoList, city);
        storedForecastRepository.saveAll(storedForecastList);
        LOGGER.info("Stored 14 days forecast for city: " + city);
    }

    public CityForecastDto getForecastForCityAndDate(String city, LocalDate date) {
        StoredForecast storedForecast = storedForecastRepository.findByDateAndCity(date, city);
        CityForecastDto cityForecastDto = forecastMapper.mapToCityForecastDto(storedForecast);
        LOGGER.info("Retrieved city forecast: " + cityForecastDto);
        return cityForecastDto;
    }
}
