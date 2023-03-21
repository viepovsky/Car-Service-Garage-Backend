package com.backend.mapper;

import com.backend.domain.Booking;
import com.backend.domain.CarService;
import com.backend.domain.Garage;
import com.backend.domain.dto.BookingDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
                booking.getCreated(),
                booking.getTotalCost(),
                booking.getCarServiceList().stream()
                        .map(CarService::getId)
                        .toList(),
                garageMapper.mapToGarageDto(booking.getGarage())
        );
    }

    public List<BookingDto> mapToBookingDtoList(List<Booking> bookingList) {
        return bookingList.stream()
                    .map(this::mapToBookingDto)
                    .toList();
    };
}
