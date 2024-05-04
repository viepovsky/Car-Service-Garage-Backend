package com.viepovsky.mapper;

import com.viepovsky.offer.dto.SelectedOfferDto;
import com.viepovsky.offer.model.SelectedOffer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OfferSelectedMapper {
    private VehicleMapper carMapper;
    private VisitMapper bookingMapper;

    public List<SelectedOfferDto> mapToCarServiceDtoList(List<SelectedOffer> carRepairList) {
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
