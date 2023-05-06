package com.viepovsky.garage.worktime;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class GarageWorkTimeDto {
    private Long id;

    @NotBlank
    private WorkDays day;

    @NotNull
    private LocalTime startHour;

    @NotNull
    private LocalTime endHour;
}
