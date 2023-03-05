package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GarageDto {
    private Long id;
    private String name;
    private String description;
    //private List<BookingDto> bookingDtoList;
}
