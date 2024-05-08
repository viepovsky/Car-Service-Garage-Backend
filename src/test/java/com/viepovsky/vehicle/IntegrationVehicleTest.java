package com.viepovsky.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.RegisterUserRequest;
import com.viepovsky.utility.scheduler.ApplicationScheduler;
import com.viepovsky.vehicle.dto.VehicleCreateRequest;
import com.viepovsky.vehicle.dto.VehicleDto;

import com.viepovsky.vehicle.dto.VehicleUpdateRequest;
import jakarta.annotation.PostConstruct;

import org.junit.jupiter.api.*;
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
import java.util.Objects;
import java.util.logging.Logger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockBean(ApplicationScheduler.class)
@DisplayName("Vehicle Integration Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationVehicleTest {
    private static final VehicleTestData TEST_DATA = new VehicleTestData();
    private static final Logger LOGGER = Logger.getLogger(IntegrationVehicleTest.class.getName());
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final RestClient REST_CLIENT = RestClient.create();
    public static String jwtToken;
    @LocalServerPort private int port;

    @BeforeAll
    static void beforeAll() {
        LOGGER.info("Before All");
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Post Construct");
    }

    @Test
    @Order(1)
    @Sql("classpath:init-vehicle-integration.sql")
    void shouldRegisterUser() throws JsonProcessingException {
        RegisterUserRequest registerUserRequest =
                RegisterUserRequest.builder()
                        .firstName("TestName")
                        .lastName("TestLastName")
                        .email("test-mail@mail.com")
                        .username(VehicleTestData.TEST_USERNAME)
                        .password("zaq1@WSXExample")
                        .build();
        String jsonRequest = objectMapper.writeValueAsString(registerUserRequest);
        ResponseEntity<AuthenticationResponse> response =
                REST_CLIENT
                        .post()
                        .uri(URI.create("http://localhost:" + port + "/v1/auth/register"))
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
                        .queryParam("username", "testuser");
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
    void shouldRetrieveVehicle() throws JsonProcessingException {
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
                                    .queryParam("username", "testuser");
        ResponseEntity<VehicleDto> response =
                REST_CLIENT
                        .put()
                        .uri(url.build().toUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .body(jsonRequest)
                        .retrieve()
                        .toEntity(VehicleDto.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


}
