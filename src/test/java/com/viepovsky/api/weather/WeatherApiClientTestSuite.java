package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.ForecastDto;
import com.viepovsky.api.weather.dto.ForecastsDto;
import com.viepovsky.api.weather.dto.LocationDto;
import com.viepovsky.api.weather.dto.LocationsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherApiClientTestSuite {
    @InjectMocks
    private WeatherApiClient client;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherApiConfig config;

    @BeforeEach
    void beforeEach() {
        when(config.getWeatherApiEndpoint()).thenReturn("https://test.com");
        when(config.getWeatherApiKey()).thenReturn("testkey");
        when(config.getWeatherApiHost()).thenReturn("testhost");
    }

    @Test
    void testGet14DaysForecast() throws URISyntaxException {
        //Given
        ForecastDto forecastDto = new ForecastDto();
        ForecastsDto forecastsDto = new ForecastsDto(LocalDate.of(2022, 10, 15), "R20", "Raining", 8, 2, 30);
        forecastDto.setForecasts(List.of(forecastsDto));

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getWeatherApiKey());
        headers.set("X-RapidAPI-Host", config.getWeatherApiHost());

        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        ResponseEntity<ForecastDto> response = new ResponseEntity<>(forecastDto, HttpStatus.OK);

        URI url = new URI("https://test.com/forecast/daily/12050?alt=0&tempunit=C&windunit=KMH&periods=14&dataset=full");

        when(restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, ForecastDto.class)).thenReturn(response);
        //When
        ForecastDto retrievedForecast = client.get14DaysForecast(12050);
        //Then
        assertEquals(1, retrievedForecast.getForecasts().size());
        assertEquals(LocalDate.of(2022,10,15), retrievedForecast.getForecasts().get(0).getDate());
        assertEquals("Raining", retrievedForecast.getForecasts().get(0).getSymbolPhrase());
    }

    @Test
    void testGetIdForCityName() throws URISyntaxException {
        //Given
        LocationDto locationDto = new LocationDto();
        LocationsDto locationsDto = new LocationsDto(12050);
        locationDto.setLocations(List.of(locationsDto));

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getWeatherApiKey());
        headers.set("X-RapidAPI-Host", config.getWeatherApiHost());

        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        ResponseEntity<LocationDto> response = new ResponseEntity<>(locationDto, HttpStatus.OK);

        URI url = new URI("https://test.com/location/search/Poznan?lang=pl&country=pl");

        when(restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, LocationDto.class)).thenReturn(response);
        //When
        LocationDto retrievedLocationDto = client.getIdForCityName("Poznan");
        //Then
        assertEquals(1, retrievedLocationDto.getLocations().size());
        assertEquals(12050, retrievedLocationDto.getLocations().get(0).getCityId());
    }
}