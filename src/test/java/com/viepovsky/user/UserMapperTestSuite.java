package com.viepovsky.user;

import com.viepovsky.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserMapperTestSuite {

    private static final UserMapper userMapper = new UserMapper();

    @Test
    void mapToUserDto() {
        //Given
        User user = new User();
        user.setId(1L);
        user.setFirstName("Test name");
        user.setLastName("Test last");
        user.setEmail("email");
        user.setPhoneNumber("252352");
        user.setUsername("username");
        //When
        UserDto mappedUser = userMapper.mapToUserDto(user);
        //Then
        assertEquals(1L, mappedUser.getId());
        assertEquals("Test name", mappedUser.getFirstName());
        assertEquals("username", mappedUser.getUsername());
    }

    @Test
    void mapToUserLogin() {
        //Given
        UserDto userDto = new UserDto();
        userDto.setFirstName("Test name");
        userDto.setLastName("Test last");
        userDto.setEmail("email");
        userDto.setPhoneNumber("252352");
        userDto.setUsername("username");
        userDto.setPassword("password");
        //When
        User mappedUser = userMapper.mapToUserLogin(userDto);
        //Then
        assertEquals("Test name", mappedUser.getFirstName());
        assertEquals("username", mappedUser.getUsername());
    }

    @Test
    void mapToUserDtoLogin() {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.ROLE_USER);
        //When
        UserDto mappedUser = userMapper.mapToUserDtoLogin(user);
        //Then
        assertEquals(1L, mappedUser.getId());
        assertEquals("username", mappedUser.getUsername());
    }
}