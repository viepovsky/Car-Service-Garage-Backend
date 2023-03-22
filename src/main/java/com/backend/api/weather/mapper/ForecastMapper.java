package com.backend.api.weather.mapper;

import com.backend.api.weather.domain.CityForecastDto;
import com.backend.api.weather.domain.ForecastDto;
import com.backend.api.weather.domain.ForecastsDto;
import com.backend.api.weather.domain.StoredForecast;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastMapper {
    public StoredForecast mapToStoredForecast(ForecastsDto forecastDto, String city) {
        return new StoredForecast(
                forecastDto.getDate(),
                forecastDto.getSymbol(),
                forecastDto.getSymbolPhrase(),
                forecastDto.getMaxTemp(),
                forecastDto.getMinTemp(),
                forecastDto.getMaxWindSpeed(),
                city
        );
    }
    public List<StoredForecast> mapToStoredForecastList(List<ForecastsDto> forecastDtoList, String city) {
        return forecastDtoList.stream()
                .map(n -> mapToStoredForecast(n, city))
                .toList();
    }

    public CityForecastDto mapToCityForecastDto(StoredForecast storedForecast) {
        return new CityForecastDto(
                storedForecast.getDate(),
                storedForecast.getSymbol(),
                storedForecast.getSymbolPhrase(),
                storedForecast.getMaxTemp(),
                storedForecast.getMinTemp(),
                storedForecast.getMaxWindSpeed(),
                storedForecast.getCity()
                );
    }
}
