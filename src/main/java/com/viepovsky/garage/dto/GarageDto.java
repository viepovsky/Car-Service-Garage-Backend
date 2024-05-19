package com.viepovsky.garage.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record GarageDto(
        Long id,
        String name,
        String description,
        AddressDto address,
        List<ScheduleDto> schedules) {}
