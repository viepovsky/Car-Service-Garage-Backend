package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Weather Api Service Test Suite")
class WeatherApiServiceTestSuite {
    @InjectMocks
    private WeatherApiService service;

    @Mock
    private WeatherApiClient client;

    @Mock
    private StoredForecastRepository repository;

    @Mock
    private ForecastMapper mapper;

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

        when(repository.findAllByCity(city)).thenReturn(new ArrayList<>());
        when(client.getIdForCityName(city)).thenReturn(locationDto);
        when(client.get14DaysForecast(56750)).thenReturn(forecastDto);
        when(mapper.mapToStoredForecastList(forecastDto.getForecasts(), city)).thenReturn(storedForecastList);
        when(repository.saveAll(storedForecastList)).thenReturn(storedForecastList);
        //When
        service.getAndStore14DaysForecast(city);
        //Then
        verify(repository, times(0)).deleteAllByCity(city);
        verify(repository, times(1)).saveAll(storedForecastList);
        assertEquals(56750, locationDto.getLocations().get(0).getCityId());
    }

    @Test
    void testGetForecastForCityAndDate() {
        //Given
        String city = "city";
        LocalDate localDate = LocalDate.now();
        StoredForecast storedForecast = Mockito.mock(StoredForecast.class);
        CityForecastDto cityForecastDto = Mockito.mock(CityForecastDto.class);
        when(repository.findByDateAndCity(localDate, city)).thenReturn(storedForecast);
        when(mapper.mapToCityForecastDto(storedForecast)).thenReturn(cityForecastDto);
        //When
        CityForecastDto retrievedCityForecast = service.getForecastForCityAndDate(city, localDate);
        //Then
        assertNotNull(retrievedCityForecast);
        verify(repository, times(1)).findByDateAndCity(localDate,city);
    }
}