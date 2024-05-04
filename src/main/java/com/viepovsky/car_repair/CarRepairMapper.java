package com.viepovsky.car_repair;

import com.viepovsky.booking.BookingMapper;
import com.viepovsky.mapper.VehicleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class CarRepairMapper {
    private VehicleMapper carMapper;
    private BookingMapper bookingMapper;

    public List<CarRepairDto> mapToCarServiceDtoList(List<ServiceSelected> carRepairList) {
        //TODO fix this
        return null;
//        return carRepairList.stream()
//                .map(n -> new CarRepairDto(
//                        n.getId(),
//                        n.getName(),
//                        n.getDescription(),
//                        n.getCost(),
//                        n.getRepairTimeInMinutes(),
//                        carMapper.mapToCarDto(n.getCar()),
//                        bookingMapper.mapToBookingDto(n.getBooking()),
//                        n.getStatus().getServiceStatus()
//                ))
//                .toList();
    }
}
