package com.viepovsky.clients.weather;

import com.viepovsky.clients.weather.dto.CityForecastDto;
import com.viepovsky.clients.weather.dto.ForecastsDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ForecastMapper {
    public StoredForecast mapToStoredForecast(ForecastsDto forecastDto, String city) {
        return new StoredForecast(
                forecastDto.date(),
                forecastDto.symbol(),
                forecastDto.symbolPhrase(),
                forecastDto.maxTemp(),
                forecastDto.minTemp(),
                forecastDto.maxWindSpeed(),
                city);
    }

    public List<StoredForecast> mapToStoredForecastList(
            List<ForecastsDto> forecastDtoList, String city) {
        return forecastDtoList.stream().map(n -> mapToStoredForecast(n, city)).toList();
    }

    public CityForecastDto mapToCityForecastDto(StoredForecast storedForecast) {
        return new CityForecastDto(
                storedForecast.getDate(),
                storedForecast.getSymbol(),
                storedForecast.getSymbolPhrase(),
                storedForecast.getMaxTemp(),
                storedForecast.getMinTemp(),
                storedForecast.getMaxWindSpeed(),
                storedForecast.getCity());
    }
}
