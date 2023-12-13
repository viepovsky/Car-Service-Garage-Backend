package com.viepovsky.security;

import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.User;
import com.viepovsky.user.UserFacade;
import com.viepovsky.user.UserService;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import com.viepovsky.user.dto.RegisterUserRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    AuthenticationResponse register(RegisterUserRequest request) {
        LOGGER.info("Register request received.");
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var createdUser = userService.saveUser(user);
        var jwtToken = jwtService.generateJwtToken(createdUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    AuthenticationResponse authenticate(AuthenticationUserRequest request) {
        LOGGER.info("Authenticate request received.");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userService.getUser(request.getUsername());
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
