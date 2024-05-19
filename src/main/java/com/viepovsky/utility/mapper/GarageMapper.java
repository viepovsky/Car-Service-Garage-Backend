package com.viepovsky.utility.mapper;

import com.viepovsky.garage.dto.AddressCreateRequest;
import com.viepovsky.garage.dto.AddressDto;
import com.viepovsky.garage.dto.GarageCreateRequest;
import com.viepovsky.garage.dto.GarageDto;
import com.viepovsky.garage.dto.ScheduleCreateRequest;
import com.viepovsky.garage.dto.ScheduleDto;
import com.viepovsky.garage.model.Address;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.model.Schedule;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GarageMapper {

    public GarageDto toGarageDto(Garage garage) {
        return new GarageDto(
                garage.getId(),
                garage.getName(),
                garage.getDescription(),
                toAddressDto(garage.getAddress()),
                toScheduleDto(garage.getGarageSchedules()));
    }

    private Address toAddress(AddressCreateRequest addressDto) {
        return new Address(addressDto.city(), addressDto.code(), addressDto.street());
    }

    public Garage toGarage(GarageCreateRequest garageDto) {
        Address address = toAddress(garageDto.address());
        Garage garage = new Garage();
        garage.setName(garageDto.name());
        garage.setDescription(garageDto.description());
        garage.setAddress(address);
        address.setGarage(garage);
        return garage;
    }

    public List<GarageDto> toGarageDtoList(List<Garage> garages) {
        return garages.stream().map(this::toGarageDto).toList();
    }

    public Schedule toSchedule(ScheduleCreateRequest request) {
        return Schedule.builder()
                .day(request.day())
                .openFrom(request.openFrom())
                .openTill(request.openTill())
                .build();
    }

    public List<ScheduleDto> toScheduleDto(List<Schedule> schedules) {
        return schedules.stream().map(this::toScheduleDto).toList();
    }

    public ScheduleDto toScheduleDto(Schedule schedule) {
        return new ScheduleDto(
                schedule.getId(),
                schedule.getDay(),
                schedule.getOpenFrom(),
                schedule.getOpenTill());
    }

    public AddressDto toAddressDto(Address address) {
        return new AddressDto(
                address.getId(), address.getCity(), address.getCode(), address.getStreet());
    }
}
