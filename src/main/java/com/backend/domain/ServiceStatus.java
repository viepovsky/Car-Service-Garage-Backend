package com.backend.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceStatus {
    NOT_ASSIGNED("Not assigned to booking table."),
    ASSIGNED("Assigned to booking table."),
    COMPLETED("Service completed."),
    NOT_COMPLETED("Service not completed.");
    private final String serviceStatus;
}
