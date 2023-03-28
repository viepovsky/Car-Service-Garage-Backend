package com.backend.api.weather.service;

import com.backend.api.weather.client.WeatherApiClient;
import com.backend.api.weather.domain.*;
import com.backend.api.weather.mapper.ForecastMapper;
import com.backend.api.weather.repository.StoredForecastRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Weather Api Service Test Suite")
class WeatherApiServiceTestSuite {
    @InjectMocks
    private WeatherApiService weatherApiService;

    @Mock
    private WeatherApiClient weatherApiClient;

    @Mock
    private StoredForecastRepository storedForecastRepository;

    @Mock
    private ForecastMapper forecastMapper;

    @Test
    void testGetAndStore14DaysForecast() {
        //Given
        String city = "Warsaw";
        LocationDto locationDto = new LocationDto(new ArrayList<>());
        LocationsDto locationsDto = new LocationsDto(56750);
        locationDto.getLocations().add(locationsDto);

        ForecastDto forecastDto = new ForecastDto(new ArrayList<>());
        ForecastsDto forecastsDto = Mockito.mock(ForecastsDto.class);
        ForecastsDto forecastsDto2 = Mockito.mock(ForecastsDto.class);
        forecastDto.getForecasts().add(forecastsDto);
        forecastDto.getForecasts().add(forecastsDto2);

        List<StoredForecast> storedForecastList = new ArrayList<>();
        StoredForecast storedForecast = Mockito.mock(StoredForecast.class);
        StoredForecast storedForecast2 = Mockito.mock(StoredForecast.class);
        storedForecastList.add(storedForecast);
        storedForecastList.add(storedForecast2);

        when(storedForecastRepository.findAllByCity(city)).thenReturn(new ArrayList<>());
        when(weatherApiClient.getIdForCityName(city)).thenReturn(locationDto);
        when(weatherApiClient.get14DaysForecast(56750)).thenReturn(forecastDto);
        when(forecastMapper.mapToStoredForecastList(forecastDto.getForecasts(), city)).thenReturn(storedForecastList);
        when(storedForecastRepository.saveAll(storedForecastList)).thenReturn(storedForecastList);
        //When
        weatherApiService.getAndStore14DaysForecast(city);
        //Then
        verify(storedForecastRepository, times(0)).deleteAllByCity(city);
        verify(storedForecastRepository, times(1)).saveAll(storedForecastList);
        assertEquals(56750, locationDto.getLocations().get(0).getCityId());
    }

    @Test
    void testGetForecastForCityAndDate() {
        //Given
        String city = "city";
        LocalDate localDate = LocalDate.now();
        StoredForecast storedForecast = Mockito.mock(StoredForecast.class);
        CityForecastDto cityForecastDto = Mockito.mock(CityForecastDto.class);
        when(storedForecastRepository.findByDateAndCity(localDate, city)).thenReturn(storedForecast);
        when(forecastMapper.mapToCityForecastDto(storedForecast)).thenReturn(cityForecastDto);
        //When
        CityForecastDto retrievedCityForecast = weatherApiService.getForecastForCityAndDate(city, localDate);
        //Then
        assertNotNull(retrievedCityForecast);
        verify(storedForecastRepository, times(1)).findByDateAndCity(localDate,city);
    }
}