package com.viepovsky.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.viepovsky.user.dto.PasswordDto;
import com.viepovsky.user.dto.UserDto;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.mappers.UserMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {
    @InjectMocks
    private UserFacade facade;

    @Mock
    private UserService service;

    @Mock
    private UserMapper mapper;

    @Test
    void shouldGetUserByUsername() {
        //Given
        AppUser mockedUser = Mockito.mock(AppUser.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(service.getUser("username")).thenReturn(mockedUser);
        when(mapper.mapToUserDto(mockedUser)).thenReturn(mockedUserDto);
        //When
        UserDto retrievedUser = facade.getUserByUsername("username");
        //Then
        assertNotNull(retrievedUser);
    }

    @Test
    void shouldGetUserToLoginByUsername() {
        //Given
        AppUser mockedUser = Mockito.mock(AppUser.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(service.getUser("username")).thenReturn(mockedUser);
        when(mapper.mapToUserDtoLogin(mockedUser)).thenReturn(mockedUserDto);
        //When
        UserDto retrievedUser = facade.getUserByUsernameToLogin("username");
        //Then
        assertNotNull(retrievedUser);
    }

    @Test
    void shouldReturnTrueIfUserRegistered() {
        //Given
        when(service.isUserInDatabase("username")).thenReturn(true);
        //When
        boolean isUserInDb = facade.isUserRegistered("username");
        //Then
        assertTrue(isUserInDb);
    }

    @Test
    void shouldGetUserPass() {
        //Given
        when(service.getUserPass("username")).thenReturn("encrypted password");
        //When
        PasswordDto passwordDto = facade.getUserPass("username");
        //Then
        assertNotNull(passwordDto);
        assertEquals("encrypted password", passwordDto.password());
    }

    @Test
    void shouldUpdateUser() {
        //Given
        AppUser mockedUser = Mockito.mock(AppUser.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(mapper.mapToUserLogin(mockedUserDto)).thenReturn(mockedUser);
        doNothing().when(service).updateUser(mockedUser);
        //When
        facade.updateUser(mockedUserDto);
        //Then
        verify(service, times(1)).updateUser(mockedUser);
    }

}