package com.viepovsky.security;


import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import com.viepovsky.user.dto.RegisterUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Validated
class AuthenticationController {
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterUserRequest request) {
        return ResponseEntity.ok(authenticationFacade.register(request));
    }

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationUserRequest request) {
        return ResponseEntity.ok(authenticationFacade.authenticate(request));
    }
}