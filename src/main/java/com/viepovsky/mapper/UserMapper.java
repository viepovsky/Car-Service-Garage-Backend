package com.viepovsky.mapper;

import com.viepovsky.user.dto.UserDto;
import com.viepovsky.user.model.AppUser;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto mapToUserDto(AppUser user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobile(),
                user.getUsername(),
                user.getCreatedDate()
        );
    }

    public AppUser mapToUserLogin(UserDto userDto) {
        return new AppUser(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getUsername(),
                userDto.getPassword()
        );
    }

    public UserDto mapToUserDtoLogin(AppUser user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
