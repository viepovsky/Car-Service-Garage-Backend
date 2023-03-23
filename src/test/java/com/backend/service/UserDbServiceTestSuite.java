package com.backend.service;

import com.backend.domain.User;
import com.backend.domain.UserRole;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("User Db Service Test Suite")
class UserDbServiceTestSuite {

    @InjectMocks
    private UserDbService userDbService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetUser() throws MyEntityNotFoundException {
        //Given
        User user = Mockito.mock(User.class);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        //When
        User retrievedUser = userDbService.getUser("username");
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
        boolean retrievedAnswer = userDbService.isUserInDatabase("username");
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
        String retrievedPass = userDbService.getUserPass("username");
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
        userDbService.saveUser(user);
        //Then
        assertEquals(UserRole.USER, user.getRole());
        assertNotNull(user.getCreatedDate());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() throws MyEntityNotFoundException {
        //Given
        User user = new User("test", "testlast", "email", "656", "testuser", "123", UserRole.USER, LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());
        User userToUpdate = new User();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);
        //When
        userDbService.updateUser(user);
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
        userDbService.deleteUser("username");
        //Then
        verify(userRepository, times(1)).deleteById(1L);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }
}