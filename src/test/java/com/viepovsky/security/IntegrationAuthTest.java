package com.viepovsky.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.UserService;
import com.viepovsky.user.dto.RegisterUserRequest;
import com.viepovsky.utility.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockBean(ApplicationScheduler.class)
@DisplayName("Vehicle Integration Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationAuthTest {
    private static final String TEST_USERNAME = "testuser22";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestClient REST_CLIENT = RestClient.create();
    private static String jwtToken;
    @LocalServerPort private int port;
    private static UserService userService;

    IntegrationAuthTest(@Autowired UserService service) {
        userService = service;
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
                        .username(TEST_USERNAME)
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

    @AfterAll
    static void deleteUserAfterRegistration() {
        userService.deleteUser(TEST_USERNAME);
    }
}
