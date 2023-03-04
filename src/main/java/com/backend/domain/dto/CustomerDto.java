package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate createdDate;
    private List<CarDto> carDtoList;
    private List<CarServiceDto> carServiceDtoList;
}
