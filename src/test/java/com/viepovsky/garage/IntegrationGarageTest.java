package com.viepovsky.garage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.viepovsky.BaseIntegrationTest;
import com.viepovsky.garage.dto.AddressCreateRequest;
import com.viepovsky.garage.dto.AddressDto;
import com.viepovsky.garage.dto.GarageCreateRequest;
import com.viepovsky.garage.dto.GarageDto;
import com.viepovsky.garage.dto.ScheduleCreateRequest;
import com.viepovsky.garage.dto.ScheduleDto;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.AuthenticationUserRequest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

class IntegrationGarageTest extends BaseIntegrationTest {
    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_ADMIN_USERNAME = "testadmin";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestClient REST_CLIENT = RestClient.create();
    private static String jwtUserToken;
    private static String jwtAdminToken;
    @LocalServerPort private int port;

    @Test
    @Order(1)
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
                GarageDto.builder().id(5000L).name("Garage1").address(addressDto).build();

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

    @Test
    @Order(4)
    void shouldRetrieveGarage() {
        AddressDto addressDto = new AddressDto(5000L, "City1", "Code1", "Street1");
        GarageDto expectedResponse =
                GarageDto.builder().id(5000L).name("Garage1").address(addressDto).build();

        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl(
                        "http://localhost:" + port + "/v1/garages/" + expectedResponse.id());
        ResponseEntity<GarageDto> response =
                REST_CLIENT
                        .get()
                        .uri(url.build().toUri())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAdminToken)
                        .retrieve()
                        .toEntity(GarageDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @Order(5)
    void shouldCreateSchedule() throws JsonProcessingException {
        ScheduleCreateRequest scheduleCreateRequest =
                ScheduleCreateRequest.builder()
                        .day(LocalDate.now())
                        .openFrom(LocalTime.of(8, 0))
                        .openTill(LocalTime.of(18, 0))
                        .build();

        Gson gson =
                new GsonBuilder()
                        .registerTypeAdapter(
                                LocalDate.class,
                                (JsonSerializer<LocalDate>)
                                        (src, type, jsonSerializationContext) ->
                                                new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                        .registerTypeAdapter(
                                LocalTime.class,
                                (JsonSerializer<LocalTime>)
                                        (src, type, jsonSerializationContext) ->
                                                new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_TIME)))
                        .create();
        String jsonRequest = gson.toJson(scheduleCreateRequest);

        ScheduleDto expectedResponse =
                ScheduleDto.builder()
                        .id(5000L)
                        .day(LocalDate.now())
                        .openFrom(LocalTime.of(8, 0))
                        .openTill(LocalTime.of(18, 0))
                        .build();

        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl(
                        "http://localhost:" + port + "/v1/garages/schedule/5000");
        ResponseEntity<ScheduleDto> response =
                REST_CLIENT
                        .post()
                        .uri(url.build().toUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAdminToken)
                        .body(jsonRequest)
                        .retrieve()
                        .toEntity(ScheduleDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "/v1/schedule/5000",
                Objects.requireNonNull(response.getHeaders().getLocation()).getPath());
        assertEquals(expectedResponse, response.getBody());
    }
}
