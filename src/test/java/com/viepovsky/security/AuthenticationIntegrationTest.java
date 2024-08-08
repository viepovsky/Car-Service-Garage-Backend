package com.viepovsky.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.BaseIntegrationTest;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.UserService;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import com.viepovsky.user.dto.RegisterUserRequest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Objects;

class AuthenticationIntegrationTest extends BaseIntegrationTest {
    private static final String TEST_USERNAME = "testuser22";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestClient REST_CLIENT = RestClient.create();
    private static String jwtToken;
    @LocalServerPort private int port;
    private static UserService userService;

    AuthenticationIntegrationTest(@Autowired UserService service) {
        userService = service;
    }

    @Test
    @Order(1)
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

    @Test
    @Order(2)
    void shouldRetrieveTokenAfterRegistration() throws JsonProcessingException {
        AuthenticationUserRequest authenticationUserRequest =
                AuthenticationUserRequest.builder()
                        .username(TEST_USERNAME)
                        .password("zaq1@WSXExample")
                        .build();
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

    @AfterAll
    static void deleteUserAfterRegistration() {
        userService.deleteUser(TEST_USERNAME);
    }
}
