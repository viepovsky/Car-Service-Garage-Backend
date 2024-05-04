package com.viepovsky.utility.mapper;

import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.visit.dto.VisitDto;
import com.viepovsky.visit.model.Visit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitMapper {

    private GarageMapper garageMapper;

    public VisitDto mapToBookingDto(Visit booking) {
        return new VisitDto(
                booking.getId(),
                booking.getStatus().toString(),
                booking.getVisitStartDate(),
                booking.getVisitStartTime(),
                booking.getVisitEndTime(),
                booking.getTotalPrice(),
                booking.getSelectedOffers().stream()
                       .map(SelectedOffer::getId)
                       .toList(),
                garageMapper.mapToGarageDto(booking.getGarage())
        );
    }

    public List<VisitDto> mapToBookingDtoList(List<Visit> bookingList) {
        return bookingList.stream()
                .map(this::mapToBookingDto)
                .toList();
    }

    ;
}
