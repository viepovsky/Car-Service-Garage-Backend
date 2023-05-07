package com.viepovsky.garage.worktime;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class GarageWorkTimeDto {
    private Long id;

    private WorkDays day;

    @NotNull
    private LocalTime startHour;

    @NotNull
    private LocalTime endHour;
}
