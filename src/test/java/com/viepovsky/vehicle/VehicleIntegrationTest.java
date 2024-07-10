package com.viepovsky.vehicle;

import static com.viepovsky.vehicle.VehicleTestData.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.BaseIntegrationTest;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import com.viepovsky.vehicle.dto.VehicleCreateRequest;
import com.viepovsky.vehicle.dto.VehicleDto;
import com.viepovsky.vehicle.dto.VehicleUpdateRequest;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

class VehicleIntegrationTest extends BaseIntegrationTest {
    private static final VehicleTestData TEST_DATA = new VehicleTestData();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestClient REST_CLIENT = RestClient.create();
    private static String jwtToken;
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
        jwtToken = Objects.requireNonNull(response.getBody()).token();
        assertNotNull(jwtToken);
    }

    @Test
    @Order(2)
    void shouldCreateVehicle() throws JsonProcessingException {
        VehicleCreateRequest request = TEST_DATA.getVehicleCreateRequest();
        String jsonRequest = objectMapper.writeValueAsString(request);

        VehicleDto expectedResponse = TEST_DATA.getVehicleDto();
        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/vehicles")
                        .queryParam("username", TEST_USERNAME);
        ResponseEntity<VehicleDto> response =
                REST_CLIENT
                        .post()
                        .uri(url.build().toUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .body(jsonRequest)
                        .retrieve()
                        .toEntity(VehicleDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "/v1/vehicles/" + expectedResponse.vehicleId(),
                Objects.requireNonNull(response.getHeaders().getLocation()).getPath());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @Order(3)
    void shouldRetrieveVehicle() {
        VehicleDto expectedResponse = TEST_DATA.getVehicleDto();
        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl(
                        "http://localhost:"
                                + port
                                + "/v1/vehicles/"
                                + expectedResponse.vehicleId());
        ResponseEntity<VehicleDto> response =
                REST_CLIENT
                        .get()
                        .uri(url.build().toUri())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .retrieve()
                        .toEntity(VehicleDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @Order(4)
    void shouldUpdateVehicle() throws JsonProcessingException {
        VehicleUpdateRequest request = TEST_DATA.getVehicleUpdateRequest();
        String jsonRequest = objectMapper.writeValueAsString(request);

        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/vehicles")
                        .queryParam("username", TEST_USERNAME);
        ResponseEntity<Void> response =
                REST_CLIENT
                        .put()
                        .uri(url.build().toUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .body(jsonRequest)
                        .retrieve()
                        .toEntity(Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(5)
    void shouldCreateNewVehicle() throws JsonProcessingException {
        VehicleCreateRequest request = TEST_DATA.getVehicleCreateRequest();
        String jsonRequest = objectMapper.writeValueAsString(request);

        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/vehicles")
                        .queryParam("username", TEST_USERNAME);
        ResponseEntity<VehicleDto> response =
                REST_CLIENT
                        .post()
                        .uri(url.build().toUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .body(jsonRequest)
                        .retrieve()
                        .toEntity(VehicleDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(6)
    void shouldRetrieveAllVehicles() {
        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/vehicles")
                        .queryParam("username", TEST_USERNAME);
        ResponseEntity<List<VehicleDto>> response =
                REST_CLIENT
                        .get()
                        .uri(url.build().toUri())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2 + 1, Objects.requireNonNull(response.getBody()).size()); //+1 initiated by SQL
    }

    @Test
    @Order(7)
    void shouldDeleteVehicle() {
        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl(
                        "http://localhost:" + port + "/v1/vehicles/" + 5000);
        ResponseEntity<Void> response =
                REST_CLIENT
                        .delete()
                        .uri(url.build().toUri())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .retrieve()
                        .toEntity(Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(8)
    void shouldRetrieveLessVehicles() {
        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/vehicles")
                        .queryParam("username", TEST_USERNAME);
        ResponseEntity<List<VehicleDto>> response =
                REST_CLIENT
                        .get()
                        .uri(url.build().toUri())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }
}
