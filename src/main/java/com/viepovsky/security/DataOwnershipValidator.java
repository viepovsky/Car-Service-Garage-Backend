package com.viepovsky.security;

import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.utility.exceptions.ForbiddenRequestException;
import com.viepovsky.vehicle.model.Vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DataOwnershipValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataOwnershipValidator.class);

    public void belongsToAuthenticatedUser(String username) {
        LOGGER.info("Validating if token belongs to given username.");
        String authTokenUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authTokenUsername.equals(username)) {
            LOGGER.warn("Token doesn't belong to given username.");
            throw new ForbiddenRequestException();
        }
        LOGGER.info("Token belongs to given username.");
    }

    public void belongsToAuthenticatedUser(Vehicle vehicle) {
        LOGGER.info("Validating if token belongs to given vehicle.");
        String authTokenUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String vehicleOwner = vehicle.getUser().getUsername();
        if (!authTokenUsername.equals(vehicleOwner)) {
            LOGGER.warn("Token doesn't belong to given vehicle.");
            throw new ForbiddenRequestException();
        }
        LOGGER.info("Token belongs to given vehicle.");
    }

    public void belongsToAuthenticatedUser(SelectedOffer selectedOffer) {
        LOGGER.info("Validating if token belongs to selected offer.");
        String authTokenUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authTokenUsername.equals(selectedOffer.getVisit().getUser().getUsername())) {
            LOGGER.warn("Token doesn't belong to selected offer.");
            throw new ForbiddenRequestException();
        }
        LOGGER.info("Token belongs to selected offer.");
    }
}
