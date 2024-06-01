package com.viepovsky.clients.car;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.clients.car.dto.CarApiDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestClientTest(CarApiClient.class)
public class CarApiClientTest {
    @Autowired private CarApiClient carApiClient;
    @Autowired private MockRestServiceServer mockServer;

    @Autowired private ObjectMapper objectMapper;

    private URI url;

    @BeforeEach
    public void setUp() throws Exception {
        int year = 2021;
        String make = "Tesla";
        String type = "Sedan";
        url =
                UriComponentsBuilder.fromHttpUrl("https://api.example.com/cars")
                        .queryParam("limit", 20)
                        .queryParam("page", 0)
                        .queryParam("year", year)
                        .queryParam("make", make)
                        .queryParam("type", type)
                        .build()
                        .encode()
                        .toUri();

        String detailsString = objectMapper.writeValueAsString(new CarApiDto("Tesla S"));

        this.mockServer
                .expect(requestTo(url))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CarApiConfig carApiConfig() {
            CarApiConfig config = new CarApiConfig();
            config.setCarApiEndpoint("https://api.example.com/cars");
            config.setCarApiKey("api-key");
            config.setCarApiHost("api-host");
            return config;
        }

        @Bean
        public RestClient restClient() {
            return RestClient.create();
        }

        @Bean
        public MockRestServiceServer mockRestServiceServer(RestClient restClient) {
            return MockRestServiceServer.createServer(restClient);
        }
    }

    @Test
    public void testGetCarModels() {

        String jsonResponse = "[{\"model\":\"Model S\"}, {\"model\":\"Model 3\"}]";

        mockServer
                .expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        List<CarApiDto> carModels = carApiClient.getCarModels(year, make, type);

        assertEquals(2, carModels.size());
        assertEquals("Model S", carModels.get(0).model());
        assertEquals("Model 3", carModels.get(1).model());
    }
}
