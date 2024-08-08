package com.viepovsky.booking;

import com.viepovsky.car_repair.CarRepair;
import com.viepovsky.garage.GarageMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingMapper {

    private GarageMapper garageMapper;

    public BookingDto mapToBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStatus().toString(),
                booking.getDate(),
                booking.getStartHour(),
                booking.getEndHour(),
                booking.getTotalCost(),
                booking.getCarRepairList().stream()
                        .map(CarRepair::getId)
                        .toList(),
                garageMapper.mapToGarageDto(booking.getGarage())
        );
    }

    public List<BookingDto> mapToBookingDtoList(List<Booking> bookingList) {
        return bookingList.stream()
                .map(this::mapToBookingDto)
                .toList();
    }

    ;
}
