package com.viepovsky.user;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Db Service Test Suite")
class UserServiceTestSuite {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetUser() throws MyEntityNotFoundException {
        //Given
        User user = Mockito.mock(User.class);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        //When
        User retrievedUser = userService.getUser("username");
        //Then
        assertNotNull(retrievedUser);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }

    @Test
    void testIsUserInDatabase() {
        //Given
        User user = Mockito.mock(User.class);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        //When
        boolean retrievedAnswer = userService.isUserInDatabase("username");
        //Then
        assertTrue(retrievedAnswer);
    }

    @Test
    void testGetUserPass() throws MyEntityNotFoundException {
        //Given
        User user = Mockito.mock(User.class);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        String pass = "1234";
        when(user.getPassword()).thenReturn(pass);
        //When
        String retrievedPass = userService.getUserPass("username");
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
        assertEquals("1234", retrievedPass);
    }

    @Test
    void testSaveUser() {
        //Given
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);
        //When
        userService.saveUser(user);
        //Then
        assertEquals(UserRole.USER, user.getRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() throws MyEntityNotFoundException {
        //Given
        User user = new User("test", "testlast", "email", "656", "testuser", "123", UserRole.USER, new ArrayList<>(), new ArrayList<>());
        User userToUpdate = new User();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);
        //When
        userService.updateUser(user);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + user.getUsername()));
        verify(userRepository, times(1)).save(userToUpdate);
        assertEquals("123", userToUpdate.getPassword());
    }

    @Test
    void testDeleteUser() throws MyEntityNotFoundException {
        //Given
        User mockedUser = Mockito.mock(User.class);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(mockedUser));
        when(mockedUser.getId()).thenReturn(1L);
        doNothing().when(userRepository).deleteById(1L);
        //When
        userService.deleteUser("username");
        //Then
        verify(userRepository, times(1)).deleteById(1L);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }
}