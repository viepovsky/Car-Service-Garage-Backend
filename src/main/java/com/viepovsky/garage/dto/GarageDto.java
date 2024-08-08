package com.viepovsky.garage.dto;


import lombok.Builder;

@Builder
public record GarageDto(
        Long id,
        String name,
        String description,
        AddressDto address) {}
