package com.viepovsky.booking;

import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.mapper.GarageMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingMapper {

    private GarageMapper garageMapper;

    public BookingDto mapToBookingDto(Visit booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStatus().toString(),
                booking.getDate(),
                booking.getStartHour(),
                booking.getEndHour(),
                booking.getTotalCost(),
                booking.getCarRepairList().stream()
                        .map(SelectedOffer::getId)
                        .toList(),
                garageMapper.mapToGarageDto(booking.getGarage())
        );
    }

    public List<BookingDto> mapToBookingDtoList(List<Visit> bookingList) {
        return bookingList.stream()
                .map(this::mapToBookingDto)
                .toList();
    }

    ;
}
