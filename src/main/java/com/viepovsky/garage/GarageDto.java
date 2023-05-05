package com.viepovsky.garage;

import com.viepovsky.garage.worktime.GarageWorkTimeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
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
