package com.viepovsky.api.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarApiClientTestSuite {
    @InjectMocks
    private CarApiClient client;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CarApiConfig config;

    @BeforeEach
    void beforeEach() {
        when(config.getCarApiEndpoint()).thenReturn("https://test.com");
        when(config.getCarApiKey()).thenReturn("testkey");
        when(config.getCarApiHost()).thenReturn("testhost");
    }

    @Test
    public void testGetCarMakes() throws URISyntaxException {
        //Given
        List<String> makeList = List.of("AUDI", "BMW", "OPEL", "PEUGEOT");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getCarApiKey());
        headers.set("X-RapidAPI-Host", config.getCarApiHost());
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        ResponseEntity<List<String>> response = new ResponseEntity<>(makeList, HttpStatus.OK);

        URI url = new URI("https://test.com/makes");
        when(restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, new ParameterizedTypeReference<List<String>>() {
        })).thenReturn(response);
        //When
        List<String> retrievedList = client.getCarMakes();
        //Then
        assertTrue(retrievedList.size() != 0);
        assertTrue(retrievedList.contains("BMW"));
    }

    @Test
    public void testGetCarYears() throws URISyntaxException {
        //Given
        List<Integer> yearList = List.of(2022, 2021, 2020, 2019);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getCarApiKey());
        headers.set("X-RapidAPI-Host", config.getCarApiHost());
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        ResponseEntity<List<Integer>> response = new ResponseEntity<>(yearList, HttpStatus.OK);

        URI url = new URI("https://test.com/years");
        when(restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, new ParameterizedTypeReference<List<Integer>>() {
        })).thenReturn(response);
        //When
        List<Integer> retrievedList = client.getCarYears();
        //Then
        assertTrue(retrievedList.size() != 0);
        assertTrue(retrievedList.contains(2020));
    }

    @Test
    public void testGetCarTypes() throws URISyntaxException {
        //Given
        List<String> typeList = List.of("Sedan", "Suv", "Hatchback", "Coupe");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getCarApiKey());
        headers.set("X-RapidAPI-Host", config.getCarApiHost());
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        ResponseEntity<List<String>> response = new ResponseEntity<>(typeList, HttpStatus.OK);

        URI url = new URI("https://test.com/types");
        when(restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, new ParameterizedTypeReference<List<String>>() {
        })).thenReturn(response);
        //When
        List<String> retrievedList = client.getCarTypes();
        //Then
        assertTrue(retrievedList.size() != 0);
        assertTrue(retrievedList.contains("Suv"));
    }

    @Test
    public void testGetCarModels() throws URISyntaxException {
        //Given
        CarApiDto[] modelTable = new CarApiDto[2];
        modelTable[0] = new CarApiDto("3 Series");
        modelTable[1] = new CarApiDto("5 Series");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getCarApiKey());
        headers.set("X-RapidAPI-Host", config.getCarApiHost());
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        ResponseEntity<CarApiDto[]> response = new ResponseEntity<>(modelTable, HttpStatus.OK);

        URI url = new URI("https://test.com?limit=20&page=0&year=2020&make=BMW&type=Sedan");
        when(restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, CarApiDto[].class)).thenReturn(response);
        //When
        List<CarApiDto> retrievedList = client.getCarModels(2020, "BMW", "Sedan");
        //Then
        assertTrue(retrievedList.size() != 0);
        assertEquals("3 Series", retrievedList.get(0).getModel());
        assertEquals("5 Series", retrievedList.get(1).getModel());
    }
}