package com.viepovsky.garage.dto;

import jakarta.validation.constraints.NotEmpty;

public record AddressCreateRequest(
        @NotEmpty String city, @NotEmpty String code, @NotEmpty String street) {}
