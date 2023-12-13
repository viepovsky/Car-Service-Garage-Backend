package com.viepovsky.user;

import com.viepovsky.user.dto.PasswordDto;
import com.viepovsky.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);
    private final UserService service;
    private final UserMapper mapper;

    public UserDto getUserByUsername(String username) {
        LOGGER.info("GET Endpoint getUserByUsername used.");
        User user = service.getUser(username);
        return mapper.mapToUserDto(user);
    }

    public UserDto getUserByUsernameToLogin(String username) {
        LOGGER.info("GET Endpoint getUserByUsernameToLogin used.");
        User user = service.getUser(username);
        return mapper.mapToUserDtoLogin(user);
    }

    public Boolean isUserRegistered(String username) {
        LOGGER.info("GET Endpoint isUserRegistered used.");
        return service.isUserInDatabase(username);
    }

    public PasswordDto getUserPass(String username) {
        LOGGER.info("GET Endpoint getUserPass used.");
        return new PasswordDto(service.getUserPass(username));
    }

    public User createUser(UserDto userDto) {
        LOGGER.info("POST Endpoint used.");
        return service.saveUser(mapper.mapToUserLogin(userDto));
    }

    public void updateUser(UserDto userDto) {
        LOGGER.info("PUT Endpoint used.");
        service.updateUser(mapper.mapToUserLogin(userDto));
    }
}
