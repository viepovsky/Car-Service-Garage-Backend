package com.viepovsky.security;

import com.viepovsky.utility.exceptions.ForbiddenRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidator.class);

    public void isValidWithAuthToken(String username) {
        LOGGER.info("Validating if token belongs to given username.");
        String authTokenUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authTokenUsername.equals(username)) {
            LOGGER.warn("Token doesn't belong to given username.");
            throw new ForbiddenRequestException();
        }
        LOGGER.info("Token belongs to given username.");
    }
}
