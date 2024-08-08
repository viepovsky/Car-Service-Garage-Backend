package com.viepovsky.car;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    private Long id;

    @NotEmpty
    private String make;

    @NotEmpty
    private String model;

    @NotEmpty
    private String type;

    @NotNull
    private int year;

    @NotNull
    private String engine;

    private Long userId;
}
