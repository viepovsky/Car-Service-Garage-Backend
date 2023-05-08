package com.viepovsky.user;

import com.viepovsky.user.dto.PasswordDto;
import com.viepovsky.user.dto.UserDto;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class UserFacadeTestSuite {
    @InjectMocks
    private UserFacade userFacade;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    void shouldGetUserByUsername() throws MyEntityNotFoundException {
        //Given
        User mockedUser = Mockito.mock(User.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(userService.getUser("username")).thenReturn(mockedUser);
        when(userMapper.mapToUserDto(mockedUser)).thenReturn(mockedUserDto);
        //When
        UserDto retrievedUser = userFacade.getUserByUsername("username");
        //Then
        assertNotNull(retrievedUser);
    }

    @Test
    void shouldGetUserToLoginByUsername() throws MyEntityNotFoundException {
        //Given
        User mockedUser = Mockito.mock(User.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(userService.getUser("username")).thenReturn(mockedUser);
        when(userMapper.mapToUserDtoLogin(mockedUser)).thenReturn(mockedUserDto);
        //When
        UserDto retrievedUser = userFacade.getUserByUsernameToLogin("username");
        //Then
        assertNotNull(retrievedUser);
    }

    @Test
    void shouldReturnTrueIfUserRegistered() {
        //Given
        when(userService.isUserInDatabase("username")).thenReturn(true);
        //When
        boolean isUserInDb = userFacade.isUserRegistered("username");
        //Then
        assertTrue(isUserInDb);
    }

    @Test
    void shouldGetUserPass() throws MyEntityNotFoundException {
        //Given
        when(userService.getUserPass("username")).thenReturn("encrypted password");
        //When
        PasswordDto passwordDto = userFacade.getUserPass("username");
        //Then
        assertNotNull(passwordDto);
        assertEquals("encrypted password", passwordDto.getPassword());
    }

    @Test
    void shouldCreateUser() {
        //Given
        User mockedUser = Mockito.mock(User.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(userMapper.mapToUserLogin(mockedUserDto)).thenReturn(mockedUser);
        when(userService.saveUser(any(User.class))).thenReturn(Mockito.mock(User.class));
        //When
        userFacade.createUser(mockedUserDto);
        //Then
        verify(userService, times(1)).saveUser(mockedUser);
    }

    @Test
    void shouldUpdateUser() throws MyEntityNotFoundException {
        //Given
        User mockedUser = Mockito.mock(User.class);
        UserDto mockedUserDto = Mockito.mock(UserDto.class);
        when(userMapper.mapToUserLogin(mockedUserDto)).thenReturn(mockedUser);
        doNothing().when(userService).updateUser(mockedUser);
        //When
        userFacade.updateUser(mockedUserDto);
        //Then
        verify(userService, times(1)).updateUser(mockedUser);
    }

}