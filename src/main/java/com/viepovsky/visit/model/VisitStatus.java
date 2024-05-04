package com.viepovsky.visit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VisitStatus {
    WAITING_FOR_CUSTOMER("Waiting for customer"),
    CUSTOMER_ABSENCE("Customer absence"),
    COMPLETED("Completed"),
    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable");

    private final String statusName;
}
