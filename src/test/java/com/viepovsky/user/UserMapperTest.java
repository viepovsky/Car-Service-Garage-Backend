package com.viepovsky.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.viepovsky.user.dto.UserDto;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.user.model.Role;
import com.viepovsky.utility.mappers.UserMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private static final UserMapper userMapper = new UserMapper();

    @Test
    void mapToUserDto() {
        //Given
        AppUser user = new AppUser();
        user.setId(1L);
        user.setFirstName("Test name");
        user.setLastName("Test last");
        user.setEmail("email");
        user.setMobile("252352");
        user.setUsername("username");
        //When
        UserDto mappedUser = userMapper.mapToUserDto(user);
        //Then
        assertEquals(1L, mappedUser.id());
        assertEquals("Test name", mappedUser.firstName());
        assertEquals("username", mappedUser.username());
    }

    @Test
    void mapToUserLogin() {
        //Given
        UserDto userDto = new UserDto(null, "username", "Test name", "Test last", null, "email", "252352", "password", null, null);
        //When
        AppUser mappedUser = userMapper.mapToUserLogin(userDto);
        //Then
        assertEquals("Test name", mappedUser.getFirstName());
        assertEquals("username", mappedUser.getUsername());
    }

    @Test
    void mapToUserDtoLogin() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.ROLE_USER);
        //When
        UserDto mappedUser = userMapper.mapToUserDtoLogin(user);
        //Then
        assertEquals(1L, mappedUser.id());
        assertEquals("username", mappedUser.username());
    }
}