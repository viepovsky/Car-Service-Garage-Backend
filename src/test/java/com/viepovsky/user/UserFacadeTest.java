package com.viepovsky.user;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.dto.PasswordDto;
import com.viepovsky.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {
    @InjectMocks
    private UserFacade facade;

    @Mock
    private UserService service;

    @Mock
    private UserMapper mapper;

    @Test
    void shouldGetUserByUsername() throws MyEntityNotFoundException {
        //Given
        User mockedUser = Mockito.mock(User.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(service.getUser("username")).thenReturn(mockedUser);
        when(mapper.mapToUserDto(mockedUser)).thenReturn(mockedUserDto);
        //When
        UserDto retrievedUser = facade.getUserByUsername("username");
        //Then
        assertNotNull(retrievedUser);
    }

    @Test
    void shouldGetUserToLoginByUsername() throws MyEntityNotFoundException {
        //Given
        User mockedUser = Mockito.mock(User.class);
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
    void shouldGetUserPass() throws MyEntityNotFoundException {
        //Given
        when(service.getUserPass("username")).thenReturn("encrypted password");
        //When
        PasswordDto passwordDto = facade.getUserPass("username");
        //Then
        assertNotNull(passwordDto);
        assertEquals("encrypted password", passwordDto.getPassword());
    }

    @Test
    void shouldCreateUser() {
        //Given
        User mockedUser = Mockito.mock(User.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(mapper.mapToUserLogin(mockedUserDto)).thenReturn(mockedUser);
        when(service.saveUser(any(User.class))).thenReturn(Mockito.mock(User.class));
        //When
        facade.createUser(mockedUserDto);
        //Then
        verify(service, times(1)).saveUser(mockedUser);
    }

    @Test
    void shouldUpdateUser() throws MyEntityNotFoundException {
        //Given
        User mockedUser = Mockito.mock(User.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(mapper.mapToUserLogin(mockedUserDto)).thenReturn(mockedUser);
        doNothing().when(service).updateUser(mockedUser);
        //When
        facade.updateUser(mockedUserDto);
        //Then
        verify(service, times(1)).updateUser(mockedUser);
    }

}