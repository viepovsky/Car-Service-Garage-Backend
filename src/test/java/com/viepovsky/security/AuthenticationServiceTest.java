package com.viepovsky.security;

import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import com.viepovsky.user.dto.RegisterUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void should_return_authentication_response_when_user_is_registered() {
        //Given
        var request = RegisterUserRequest.builder().username("testusername")
                .password("TestPassword20@").email("test@mail.com").firstName("name").lastName("lastname").build();
        var user = User.builder().username("testusername").password("TestPassword20@")
                .email("test@mail.com").firstName("name").lastName("lastname").build();
        var jwtToken = "token";

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(jwtService.generateJwtToken(any(User.class))).thenReturn(jwtToken);
        //When
        var response = authenticationService.register(request);
        //Then
        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());
    }

    @Test
    void should_return_authentication_response_when_user_is_authenticated() {
        //Given
        var request = AuthenticationUserRequest.builder()
                .username("testusername").password("TestPassword20@").build();
        var user = User.builder().username("testusername").password("TestPassword20@")
                .email("test@mail.com").firstName("name").lastName("lastname").build();
        var jwtToken = "token";

        when(authenticationManager.authenticate(any())).thenReturn(new TestingAuthenticationToken(null, null));
        when(userService.getUser(anyString())).thenReturn(user);
        when(jwtService.generateJwtToken(any(User.class))).thenReturn(jwtToken);

        //When
        var response = authenticationService.authenticate(request);
        //Then
        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());
    }
}