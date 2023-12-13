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
    private final UserService userService;
    private final UserMapper mapper;

    public UserDto getUserByUsername(String username) {
        LOGGER.info("Get user endpoint used with username:{}", username);
        User user = userService.getUser(username);
        return mapper.mapToUserDto(user);
    }

    public UserDto getUserByUsernameToLogin(String username) {
        LOGGER.info("Get user for login endpoint used with username:{}", username);
        User user = userService.getUser(username);
        return mapper.mapToUserDtoLogin(user);
    }

    public Boolean isUserRegistered(String username) {
        LOGGER.info("Is user registered endpoint used with username:{}", username);
        return userService.isUserInDatabase(username);
    }

    public PasswordDto getUserPass(String username) {
        LOGGER.info("Get user password endpoint used for username:{}", username);
        return new PasswordDto(userService.getUserPass(username));
    }

    public User createUser(UserDto userDto) {
        LOGGER.info("Create user endpoint used with username:{}", userDto.getUsername());
        return userService.saveUser(mapper.mapToUserLogin(userDto));
    }

    public void updateUser(UserDto userDto) {
        LOGGER.info("Update user endpoint used with username:{}", userDto.getUsername());
        userService.updateUser(mapper.mapToUserLogin(userDto));
    }
}
