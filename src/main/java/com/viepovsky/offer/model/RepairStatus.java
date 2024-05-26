package com.viepovsky.offer.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RepairStatus {
    NOT_ASSIGNED("Not assigned to visit table."),
    ASSIGNED("Assigned to visit table."),
    AWAITING("Awaiting customer"),
    NOT_STARTED("Service not started"),
    STARTED("Service started"),
    IN_PROGRESS("Service in progress"),
    COMPLETED("Service completed."),
    SUSPENDED("Suspended - awaiting customer decision"),
    CANCELLED("Service cancelled");
    private final String serviceStatus;
}
