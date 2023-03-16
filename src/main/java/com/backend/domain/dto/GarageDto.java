package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class GarageDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
