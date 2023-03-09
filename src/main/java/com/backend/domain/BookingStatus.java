package com.backend.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {

    WAITING_FOR_CUSTOMER("Waiting for customer"),
    CUSTOMER_ABSENCE("Customer absence"),
    COMPLETED("Completed"),
    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable");

    private final String statusName;
}
