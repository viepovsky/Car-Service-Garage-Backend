package com.viepovsky.visit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.viepovsky.BaseIntegrationTest;
import com.viepovsky.garage.dto.*;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IntegrationVisitTest extends BaseIntegrationTest {
    private static final String TEST_USERNAME = "testuser";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestClient REST_CLIENT = RestClient.create();
    private static String jwtUserToken;
    @LocalServerPort
    private int port;

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
    void shouldGetAvailableTimes() throws JsonProcessingException {
//        LocalDate date = LocalDate.of(2024, 3, 3);
        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl(
                                "http://localhost:" + port + "/v1/visits/new-offer-available-times")
                        .queryParam("date", "2100-03-03")
                        .queryParam("repair-duration",60)
                        .queryParam("garage-id", 1);
        Logger.getAnonymousLogger().info(url.toUriString());
        ResponseEntity<List<LocalTime>> response =
                REST_CLIENT
                        .get()
                        .uri(url.build().toUri())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUserToken)
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(43, response.getBody().size()); //7:00 to 14:00 intervals 10minutes
    }

//    @Test
//    @Order(3)
//    void shouldCreateVisit() throws JsonProcessingException {
//        AddressCreateRequest addressRequest = new AddressCreateRequest("City1", "Code1", "Street1");
//        GarageCreateRequest garageRequest =
//                GarageCreateRequest.builder().name("Garage1").address(addressRequest).build();
//        String jsonRequest = objectMapper.writeValueAsString(garageRequest);
//
//        AddressDto addressDto = new AddressDto(5000L, "City1", "Code1", "Street1");
//        GarageDto expectedResponse =
//                GarageDto.builder().id(5000L).name("Garage1").address(addressDto).build();
//
//        UriComponentsBuilder url =
//                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/garages");
//        ResponseEntity<GarageDto> response =
//                REST_CLIENT
//                        .post()
//                        .uri(url.build().toUri())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAdminToken)
//                        .body(jsonRequest)
//                        .retrieve()
//                        .toEntity(GarageDto.class);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(
//                "/v1/garages/" + expectedResponse.id(),
//                Objects.requireNonNull(response.getHeaders().getLocation()).getPath());
//        assertEquals(expectedResponse, response.getBody());
//    }
//
//    @Test
//    @Order(4)
//    void shouldRetrieveGarage() {
//        AddressDto addressDto = new AddressDto(5000L, "City1", "Code1", "Street1");
//        GarageDto expectedResponse =
//                GarageDto.builder().id(5000L).name("Garage1").address(addressDto).build();
//
//        UriComponentsBuilder url =
//                UriComponentsBuilder.fromHttpUrl(
//                        "http://localhost:" + port + "/v1/garages/" + expectedResponse.id());
//        ResponseEntity<GarageDto> response =
//                REST_CLIENT
//                        .get()
//                        .uri(url.build().toUri())
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUserToken)
//                        .retrieve()
//                        .toEntity(GarageDto.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedResponse, response.getBody());
//    }
//
//    @Test
//    @Order(5)
//    void shouldCreateSchedule() {
//        ScheduleCreateRequest scheduleCreateRequest =
//                ScheduleCreateRequest.builder()
//                                     .date(LocalDate.now())
//                                     .openTime(LocalTime.of(8, 0))
//                                     .closeTime(LocalTime.of(18, 0))
//                                     .build();
//
//        Gson gson =
//                new GsonBuilder()
//                        .registerTypeAdapter(
//                                LocalDate.class,
//                                (JsonSerializer<LocalDate>)
//                                        (src, type, jsonSerializationContext) ->
//                                                new JsonPrimitive(
//                                                        src.format(
//                                                                DateTimeFormatter.ISO_LOCAL_DATE)))
//                        .registerTypeAdapter(
//                                LocalTime.class,
//                                (JsonSerializer<LocalTime>)
//                                        (src, type, jsonSerializationContext) ->
//                                                new JsonPrimitive(
//                                                        src.format(
//                                                                DateTimeFormatter.ISO_LOCAL_TIME)))
//                        .create();
//        String jsonRequest = gson.toJson(scheduleCreateRequest);
//
//        ScheduleDto expectedResponse =
//                ScheduleDto.builder()
//                           .id(5000L)
//                           .day(LocalDate.now())
//                           .openFrom(LocalTime.of(8, 0))
//                           .openTill(LocalTime.of(18, 0))
//                           .build();
//
//        UriComponentsBuilder url =
//                UriComponentsBuilder.fromHttpUrl(
//                        "http://localhost:" + port + "/v1/garages/schedule/5000");
//        ResponseEntity<ScheduleDto> response =
//                REST_CLIENT
//                        .post()
//                        .uri(url.build().toUri())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAdminToken)
//                        .body(jsonRequest)
//                        .retrieve()
//                        .toEntity(ScheduleDto.class);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(
//                "/v1/garages/schedule/5000",
//                Objects.requireNonNull(response.getHeaders().getLocation()).getPath());
//        assertEquals(expectedResponse, response.getBody());
//    }
//
//    @Test
//    @Order(6)
//    void shouldRetrieveSchedule() {
//        ScheduleDto expectedResponse =
//                ScheduleDto.builder()
//                           .id(5000L)
//                           .day(LocalDate.now())
//                           .openFrom(LocalTime.of(8, 0))
//                           .openTill(LocalTime.of(18, 0))
//                           .build();
//
//        UriComponentsBuilder url =
//                UriComponentsBuilder.fromHttpUrl(
//                        "http://localhost:"
//                                + port
//                                + "/v1/garages/schedule/"
//                                + expectedResponse.id());
//        ResponseEntity<List<ScheduleDto>> response =
//                REST_CLIENT
//                        .get()
//                        .uri(url.build().toUri())
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUserToken)
//                        .retrieve()
//                        .toEntity(new ParameterizedTypeReference<>() {});
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedResponse, Objects.requireNonNull(response.getBody()).get(0));
//    }
//
//    @Test
//    @Order(7)
//    void shouldDeleteSchedule() {
//        UriComponentsBuilder url =
//                UriComponentsBuilder.fromHttpUrl(
//                        "http://localhost:" + port + "/v1/garages/schedule/" + 5000);
//        ResponseEntity<Void> response =
//                REST_CLIENT
//                        .delete()
//                        .uri(url.build().toUri())
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAdminToken)
//                        .retrieve()
//                        .toEntity(Void.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    @Order(7)
//    void shouldDeleteGarage() {
//        UriComponentsBuilder url =
//                UriComponentsBuilder.fromHttpUrl(
//                        "http://localhost:" + port + "/v1/garages/" + 5000);
//        ResponseEntity<Void> response =
//                REST_CLIENT
//                        .delete()
//                        .uri(url.build().toUri())
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAdminToken)
//                        .retrieve()
//                        .toEntity(Void.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    @Order(8)
//    void shouldRetrieveNoneGarages() {
//        UriComponentsBuilder url =
//                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/garages");
//        ResponseEntity<List<GarageDto>> response =
//                REST_CLIENT
//                        .get()
//                        .uri(url.build().toUri())
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAdminToken)
//                        .retrieve()
//                        .toEntity(new ParameterizedTypeReference<>() {});
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(0, Objects.requireNonNull(response.getBody()).size());
//    }
}
