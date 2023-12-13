package com.viepovsky.security;

import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import com.viepovsky.user.dto.RegisterUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AuthenticationFacade {
    private final AuthenticationService authenticationService;

    AuthenticationResponse register(RegisterUserRequest request) {
        return authenticationService.register(request);
    }

    AuthenticationResponse authenticate(AuthenticationUserRequest request) {
        return authenticationService.authenticate(request);
    }
}
