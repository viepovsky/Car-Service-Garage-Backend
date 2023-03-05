package com.backend.mapper;

import com.backend.domain.User;
import com.backend.domain.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getCustomer().getId()
        );
    }

    public User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getPassword()
        );
    }
}
