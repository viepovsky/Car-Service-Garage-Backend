package com.viepovsky.garage.garage_work_time;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class GarageWorkTimeDto {

    private Long id;

    private WorkDays day;

    @NotNull
    private LocalTime startHour;

    @NotNull
    private LocalTime endHour;
}
