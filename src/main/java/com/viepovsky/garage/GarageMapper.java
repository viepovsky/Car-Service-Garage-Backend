package com.viepovsky.garage;

import com.viepovsky.garage.garage_work_time.GarageWorkTimeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GarageMapper {

    private GarageWorkTimeMapper garageWorkTimeMapper;

    public GarageDto mapToGarageDto(Garage garage) {
        //TODO fixthis
        return null;
//        return new GarageDto(
//                garage.getId(),
//                garage.getName(),
//                garage.getAddress(),
//                garageWorkTimeMapper.mapToGarageWorkTimeDtoList(garage.getGarageWorkTimeList())
//        );
    }

    public Garage mapToGarage(GarageDto garageDto) {
        //TODO fix this
        return null;
//        return new Garage(
//                garageDto.getName(),
//                garageDto.getAddress()
//        );
    }

    public List<GarageDto> mapToGarageDtoList(List<Garage> garageList) {
        return garageList.stream()
                .map(this::mapToGarageDto)
                .toList();
    }
}
