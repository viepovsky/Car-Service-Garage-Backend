package com.viepovsky.garage;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.garage.dto.AddressCreateRequest;
import com.viepovsky.garage.dto.AddressDto;
import com.viepovsky.garage.dto.GarageCreateRequest;
import com.viepovsky.garage.dto.GarageDto;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import com.viepovsky.utility.scheduler.ApplicationScheduler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockBean(ApplicationScheduler.class)
@DisplayName("Garage Integration Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationGarageTest {
    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_ADMIN_USERNAME = "testadmin";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestClient REST_CLIENT = RestClient.create();
    private static String jwtUserToken;
    private static String jwtAdminToken;
    @LocalServerPort private int port;

    @Test
    @Order(1)
    @Sql("classpath:init-admin.sql")
    void shouldGetJwtForNormalUser() throws JsonProcessingException {
        AuthenticationUserRequest authenticationUserRequest =
                new AuthenticationUserRequest(TEST_USERNAME, "testpassword");
        String jsonRequest = objectMapper.writeValueAsString(authenticationUserRequest);
        ResponseEntity<AuthenticationResponse> response =
                REST_CLIENT
                        .post()
                        .uri(URI.create("http://localhost:" + port + "/v1/auth/authenticate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonRequest)
                        .retrieve()
                        .toEntity(AuthenticationResponse.class);
        jwtUserToken = Objects.requireNonNull(response.getBody()).token();
        assertNotNull(jwtUserToken);
    }

    @Test
    @Order(2)
    void shouldGetJwtForAdminUser() throws JsonProcessingException {
        AuthenticationUserRequest authenticationAdminRequest =
                new AuthenticationUserRequest(TEST_ADMIN_USERNAME, "testpassword");
        String jsonRequest = objectMapper.writeValueAsString(authenticationAdminRequest);
        ResponseEntity<AuthenticationResponse> response =
                REST_CLIENT
                        .post()
                        .uri(URI.create("http://localhost:" + port + "/v1/auth/authenticate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonRequest)
                        .retrieve()
                        .toEntity(AuthenticationResponse.class);
        jwtAdminToken = Objects.requireNonNull(response.getBody()).token();
        assertNotNull(jwtAdminToken);
    }

    @Test
    @Order(3)
    void shouldCreateGarage() throws JsonProcessingException {
        AddressCreateRequest addressRequest = new AddressCreateRequest("City1", "Code1", "Street1");
        GarageCreateRequest garageRequest =
                GarageCreateRequest.builder().name("Garage1").address(addressRequest).build();
        String jsonRequest = objectMapper.writeValueAsString(garageRequest);

        AddressDto addressDto = new AddressDto(5000L, "City1", "Code1", "Street1");
        GarageDto expectedResponse =
                GarageDto.builder().id(5000L).name("Garage1").address(addressDto).schedules(new ArrayList<>()).build();

        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/garages");
        ResponseEntity<GarageDto> response =
                REST_CLIENT
                        .post()
                        .uri(url.build().toUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAdminToken)
                        .body(jsonRequest)
                        .retrieve()
                        .toEntity(GarageDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "/v1/garages/" + expectedResponse.id(),
                Objects.requireNonNull(response.getHeaders().getLocation()).getPath());
        assertEquals(expectedResponse, response.getBody());
    }
}
