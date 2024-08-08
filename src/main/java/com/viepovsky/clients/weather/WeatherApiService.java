package com.viepovsky.clients.weather;

import com.viepovsky.clients.weather.dto.CityForecastDto;
import com.viepovsky.clients.weather.dto.ForecastDto;
import com.viepovsky.clients.weather.dto.ForecastsDto;
import com.viepovsky.clients.weather.dto.LocationDto;
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

    private final ForecastMapper mapper;

    public void getAndStore14DaysForecast(String city) {
        if (!storedForecastRepository.findAllByCity(city).isEmpty()) {
            storedForecastRepository.deleteAllByCity(city);
        }
        LocationDto locationDto = weatherApiClient.getIdForCityName(city);
        int cityId = locationDto.getLocations().get(0).getCityId();
        ForecastDto forecastDto = weatherApiClient.get14DaysForecast(cityId);
        List<ForecastsDto> forecastsDtoList = forecastDto.getForecasts();
        List<StoredForecast> storedForecastList = mapper.mapToStoredForecastList(forecastsDtoList, city);
        storedForecastRepository.saveAll(storedForecastList);
        LOGGER.info("Stored 14 days forecast for city: " + city);
    }

    public CityForecastDto getForecastForCityAndDate(String city, LocalDate date) {
        StoredForecast storedForecast = storedForecastRepository.findByDateAndCity(date, city);
        CityForecastDto cityForecastDto = mapper.mapToCityForecastDto(storedForecast);
        LOGGER.info("Retrieved city forecast: " + cityForecastDto);
        return cityForecastDto;
    }
}
