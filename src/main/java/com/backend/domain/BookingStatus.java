package com.backend.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {

    AWAITING("Awaiting customer"),
    ABSENCE("Customer absence"),
    COMPLETED("Service completed");

    private final String statusName;
}
