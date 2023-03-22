package com.backend.api.weather.mapper;

import com.backend.api.weather.domain.ForecastDto;
import com.backend.api.weather.domain.StoredForecast;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastMapper {
    public StoredForecast mapToStoredForecast(ForecastDto forecastDto, String city) {
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
    public List<StoredForecast> mapToStoredForecastList(List<ForecastDto> forecastDtoList, String city) {
        return forecastDtoList.stream()
                .map(n -> mapToStoredForecast(n, city))
                .toList();
    }
}
