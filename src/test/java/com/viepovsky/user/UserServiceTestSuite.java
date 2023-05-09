package com.viepovsky.user;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Db Service Test Suite")
class UserServiceTestSuite {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Test
    void testGetUserByUsername() throws MyEntityNotFoundException {
        //Given
        var user = new User();
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        //When
        var retrievedUser = service.getUser("username");
        //Then
        assertNotNull(retrievedUser);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }

    @Test
    void testGetUserById() throws MyEntityNotFoundException {
        //Given
        var user = new User();
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));
        //When
        var retrievedUser = service.getUser(5L);
        //Then
        assertNotNull(retrievedUser);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }

    @Test
    void testIsUserInDatabase() {
        //Given
        var user = new User();
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        //When
        boolean retrievedAnswer = service.isUserInDatabase("username");
        //Then
        assertTrue(retrievedAnswer);
    }

    @Test
    void testGetUserPass() throws MyEntityNotFoundException {
        //Given
        var user = new User();
        user.setPassword("1234");
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        //When
        String retrievedPass = service.getUserPass("username");
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
        assertEquals("1234", retrievedPass);
    }

    @Test
    void testSaveUser() {
        //Given
        var user = new User();
        when(repository.save(user)).thenReturn(user);
        //When
        service.saveUser(user);
        //Then
        verify(repository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() throws MyEntityNotFoundException {
        //Given
        var user = new User("test", "testlast", "email", "656", "testuser", "123", Role.ROLE_USER, new ArrayList<>(), new ArrayList<>());
        var userToUpdate = new User();
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(userToUpdate));
        when(repository.save(userToUpdate)).thenReturn(userToUpdate);
        //When
        service.updateUser(user);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + user.getUsername()));
        verify(repository, times(1)).save(userToUpdate);
        assertEquals("123", userToUpdate.getPassword());
    }

    @Test
    void testDeleteUser() throws MyEntityNotFoundException {
        //Given
        var user = new User();
        user.setId(1L);
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        doNothing().when(repository).deleteById(anyLong());
        //When
        service.deleteUser("username");
        //Then
        verify(repository, times(1)).deleteById(anyLong());
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }
}