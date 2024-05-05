package com.viepovsky.security;

import com.viepovsky.utility.exceptions.ForbiddenRequestException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    public void isValidWithAuthToken(String username) {
        String authTokenUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authTokenUsername.equals(username)) throw new ForbiddenRequestException();
    }
}
