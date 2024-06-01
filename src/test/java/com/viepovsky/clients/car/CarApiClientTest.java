package com.viepovsky.clients.car;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.viepovsky.clients.car.dto.CarApiDto;
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

@ExtendWith(MockitoExtension.class)
class CarApiClientTest {
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
        assertFalse(retrievedList.isEmpty());
        assertEquals("3 Series", retrievedList.get(0).model());
        assertEquals("5 Series", retrievedList.get(1).model());
    }
}