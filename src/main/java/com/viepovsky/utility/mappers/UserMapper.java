package com.viepovsky.utility.mappers;

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
                userDto.firstName(),
                userDto.lastName(),
                userDto.email(),
                userDto.mobile(),
                userDto.username(),
                userDto.password()
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
