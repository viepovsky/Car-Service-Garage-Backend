package com.backend.mapper;

import com.backend.domain.User;
import com.backend.domain.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedDate()
        );
    }

    public User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getCreatedDate()
        );
    }

    public User mapToUserLogin(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getRole()
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
