package com.viepovsky.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.scheduler.ApplicationScheduler;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import com.viepovsky.user.dto.RegisterUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationFacade facade;

    @Test
    void should_get_token_after_registering_user() throws Exception {
        //Given
        var request = RegisterUserRequest.builder().username("testusername")
                .password("TestPassword20@").email("test@mail.com").firstName("name").lastName("lastname").build();
        var jsonRequest = new ObjectMapper().writeValueAsString(request);
        var response = new AuthenticationResponse("token");
        var jsonResponse = new ObjectMapper().writeValueAsString(response);

        when(facade.register(any(RegisterUserRequest.class))).thenReturn(response);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }

    @Test
    void should_get_token_after_authenticating_user() throws Exception {
        //Given
        var request = AuthenticationUserRequest.builder()
                .username("testusername").password("TestPassword20@").build();
        var jsonRequest = new ObjectMapper().writeValueAsString(request);
        var response = new AuthenticationResponse("token");
        var jsonResponse = new ObjectMapper().writeValueAsString(response);

        when(facade.authenticate(any(AuthenticationUserRequest.class))).thenReturn(response);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }

}