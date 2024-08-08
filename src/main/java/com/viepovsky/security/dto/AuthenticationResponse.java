package com.viepovsky.security.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {}
