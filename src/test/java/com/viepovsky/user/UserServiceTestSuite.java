package com.viepovsky.user;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    void testGetUser() throws MyEntityNotFoundException {
        //Given
        User user = Mockito.mock(User.class);
        when(repository.findByUsername("username")).thenReturn(Optional.of(user));
        //When
        User retrievedUser = service.getUser("username");
        //Then
        assertNotNull(retrievedUser);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }

    @Test
    void testIsUserInDatabase() {
        //Given
        User user = Mockito.mock(User.class);
        when(repository.findByUsername("username")).thenReturn(Optional.of(user));
        //When
        boolean retrievedAnswer = service.isUserInDatabase("username");
        //Then
        assertTrue(retrievedAnswer);
    }

    @Test
    void testGetUserPass() throws MyEntityNotFoundException {
        //Given
        User user = Mockito.mock(User.class);
        when(repository.findByUsername("username")).thenReturn(Optional.of(user));
        String pass = "1234";
        when(user.getPassword()).thenReturn(pass);
        //When
        String retrievedPass = service.getUserPass("username");
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
        assertEquals("1234", retrievedPass);
    }

    @Test
    void testSaveUser() {
        //Given
        User user = new User();
        when(repository.save(user)).thenReturn(user);
        //When
        service.saveUser(user);
        //Then
        verify(repository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() throws MyEntityNotFoundException {
        //Given
        User user = new User("test", "testlast", "email", "656", "testuser", "123", Role.ROLE_USER, new ArrayList<>(), new ArrayList<>());
        User userToUpdate = new User();
        when(repository.findByUsername("testuser")).thenReturn(Optional.of(userToUpdate));
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
        User mockedUser = Mockito.mock(User.class);
        when(repository.findByUsername("username")).thenReturn(Optional.of(mockedUser));
        when(mockedUser.getId()).thenReturn(1L);
        doNothing().when(repository).deleteById(1L);
        //When
        service.deleteUser("username");
        //Then
        verify(repository, times(1)).deleteById(1L);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }
}