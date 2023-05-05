package com.viepovsky.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
