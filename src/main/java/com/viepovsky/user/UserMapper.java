package com.viepovsky.user;

import com.viepovsky.user.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
class UserMapper {

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getUsername(),
                user.getCreatedDate()
        );
    }

    public User mapToUserLogin(UserDto userDto) {
        return new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getUsername(),
                userDto.getPassword()
        );
    }

    public UserDto mapToUserDtoLogin(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
