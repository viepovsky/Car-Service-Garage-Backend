package com.viepovsky.utility.mapper;

import com.viepovsky.garage.dto.AddressCreateRequest;
import com.viepovsky.garage.dto.CreateScheduleRequest;
import com.viepovsky.garage.dto.GarageCreateRequest;
import com.viepovsky.garage.dto.GarageDto;
import com.viepovsky.garage.model.Address;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.model.Schedule;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GarageMapper {

    private ScheduleMapper garageWorkTimeMapper;

    public GarageDto mapToGarageDto(Garage garage) {
        // TODO fixthis
        return null;
        //        return new GarageDto(
        //                garage.getId(),
        //                garage.getName(),
        //                garage.getAddress(),
        //
        // garageWorkTimeMapper.mapToGarageWorkTimeDtoList(garage.getGarageWorkTimeList())
        //        );
    }

    private Address toAddress(AddressCreateRequest addressDto) {
        return new Address(addressDto.city(), addressDto.code(), addressDto.street());
    }

    public Garage toGarage(GarageCreateRequest garageDto) {
        Address address = toAddress(garageDto.address());
        Garage garage =
                Garage.builder()
                        .name(garageDto.name())
                        .description(garageDto.description())
                        .address(address)
                        .build();
        address.setGarage(garage);
        return garage;
    }

    public Garage mapToGarage(GarageDto garageDto) {
        // TODO fix this
        return null;
        //        return new Garage(
        //                garageDto.getName(),
        //                garageDto.getAddress()
        //        );
    }

    public List<GarageDto> mapToGarageDtoList(List<Garage> garageList) {
        return garageList.stream().map(this::mapToGarageDto).toList();
    }

    public Schedule toSchedule(CreateScheduleRequest request) {
        return Schedule.builder()
                .day(request.day())
                .openFrom(request.openFrom())
                .openTill(request.openTill())
                .build();
    }
}
