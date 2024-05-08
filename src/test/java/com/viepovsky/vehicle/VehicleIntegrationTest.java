package com.viepovsky.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.RegisterUserRequest;
import com.viepovsky.utility.scheduler.ApplicationScheduler;
import com.viepovsky.vehicle.dto.VehicleCreateRequest;
import com.viepovsky.vehicle.dto.VehicleDto;

import jakarta.annotation.PostConstruct;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
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
public class VehicleIntegrationTest {
    private static final VehicleTestData TEST_DATA = new VehicleTestData();
    private static final Logger LOGGER = Logger.getLogger(VehicleIntegrationTest.class.getName());
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final RestClient REST_CLIENT = RestClient.create();
    public static String jwtToken;
    @LocalServerPort private int port;

    @BeforeAll
    static void beforeAll() throws JsonProcessingException {
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
    }

    @Test
    @Order(2)
    void shouldCreateVehicle() throws JsonProcessingException {
        VehicleCreateRequest request = TEST_DATA.getVehicleCreateRequest();
        String jsonRequest = objectMapper.writeValueAsString(request);
        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/vehicles")
                        .queryParam("username", "testuser");
        VehicleDto expectedResponse = TEST_DATA.getVehicleDto();
        ResponseEntity<VehicleDto> response =
                REST_CLIENT
                        .post()
                        .uri(url.build().toUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .body(jsonRequest)
                        .retrieve()
                        .toEntity(VehicleDto.class);
        assertEquals(expectedResponse, response.getBody());
    }
}
