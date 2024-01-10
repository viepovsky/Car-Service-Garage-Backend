package com.viepovsky.garage;

import com.viepovsky.garage.garage_work_time.GarageWorkTimeDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GarageDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    private List<GarageWorkTimeDto> garageWorkTimeDtoList;
}
