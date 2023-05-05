package com.viepovsky.user;

import com.viepovsky.exceptions.MyEntityNotFoundException;
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
    private final UserService userService;
    private final UserMapper userMapper;

    public UserDto getUserByUsername(String username) throws MyEntityNotFoundException {
        LOGGER.info("GET Endpoint getUserByUsername used.");
        User user = userService.getUser(username);
        return userMapper.mapToUserDto(user);
    }

    public UserDto getUserByUsernameToLogin(String username) throws MyEntityNotFoundException {
        LOGGER.info("GET Endpoint getUserByUsernameToLogin used.");
        User user = userService.getUser(username);
        return userMapper.mapToUserDtoLogin(user);
    }

    public Boolean isUserRegistered(String username) {
        LOGGER.info("GET Endpoint isUserRegistered used.");
        return userService.isUserInDatabase(username);
    }

    public PasswordDto getUserPass(String username) throws MyEntityNotFoundException {
        LOGGER.info("GET Endpoint getUserPass used.");
        return new PasswordDto(userService.getUserPass(username));
    }

    public void createUser(UserDto userDto) {
        LOGGER.info("POST Endpoint used.");
        userService.saveUser(userMapper.mapToUserLogin(userDto));
    }

    public void updateUser(UserDto userDto) throws MyEntityNotFoundException {
        LOGGER.info("PUT Endpoint used.");
        userService.updateUser(userMapper.mapToUserLogin(userDto));
    }
}
