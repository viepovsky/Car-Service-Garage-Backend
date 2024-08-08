package com.viepovsky.utility.mappers;

import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.visit.dto.VisitDto;
import com.viepovsky.visit.dto.VisitFullDetailDto;
import com.viepovsky.visit.model.Visit;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitMapper {
    private GarageMapper garageMapper;
    
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

    public VisitFullDetailDto toFullDetailVisitDto(Visit visit) {
        return new VisitFullDetailDto(
                visit.getId(),
                visit.getVisitStartDate(),
                visit.getVisitStartTime(),
                visit.getVisitEndDate(),
                visit.getVisitEndTime(),
                visit.getLicensePlate(),
                visit.getTotalPrice(),
                visit.getStatus().name(),
                visit.getSelectedOffers().stream().map(SelectedOffer::getId).toList(),
                visit.getGarage().getId(),
                visit.getVehicle().getId());
    }

    public List<VisitFullDetailDto> toFullDetailVisitDto(List<Visit> visits) {
        return visits.stream().map(this::toFullDetailVisitDto).toList();
    }
}
