package com.backend.mapper;

import com.backend.domain.Booking;
import com.backend.domain.dto.BookingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingMapper {
    private final CarServiceMapper carServiceMapper;

    public BookingDto mapToBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStatus().toString(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getCreated(),
                booking.getCarServiceList().stream()
                        .map(carServiceMapper::mapToCarServiceDto)
                        .toList()
        );
    }

    public List<BookingDto> mapToBookingDtoList(List<Booking> bookingList) {
        return bookingList.stream()
                    .map(this::mapToBookingDto)
                    .toList();
    };
}
