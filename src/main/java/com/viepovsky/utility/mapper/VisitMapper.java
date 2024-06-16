package com.viepovsky.utility.mapper;

import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.visit.dto.VisitDto;
import com.viepovsky.visit.dto.VisitOldDto;
import com.viepovsky.visit.model.Visit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitMapper {
    private GarageMapper garageMapper;

    public VisitOldDto mapToBookingDto(Visit booking) {
        return new VisitOldDto(
                booking.getId(),
                booking.getStatus().toString(),
                booking.getVisitStartDate(),
                booking.getVisitStartTime(),
                booking.getVisitEndTime(),
                booking.getTotalPrice(),
                booking.getSelectedOffers().stream()
                       .map(SelectedOffer::getId)
                       .toList(),
                garageMapper.toGarageDto(booking.getGarage())
        );
    }

    public VisitDto toVisitDto(Visit visit) {
        return new VisitDto(
                visit.getId(),
                visit.getVisitStartDate(),
                visit.getVisitStartTime(),
                visit.getVisitEndDate(),
                visit.getVisitEndTime());
    }

    public List<VisitDto> toVisitDto(List<Visit> visits) {
        return visits.stream().map(this::toVisitDto).toList();
    }

    public List<VisitOldDto> mapToBookingDtoList(List<Visit> bookingList) {
        return bookingList.stream()
                .map(this::mapToBookingDto)
                .toList();
    }

    ;
}
