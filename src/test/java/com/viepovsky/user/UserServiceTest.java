package com.viepovsky.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.viepovsky.utility.exceptions.MyEntityNotFoundException;

import com.viepovsky.user.model.AppUser;
import com.viepovsky.user.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Db Service Test")
class UserServiceTest {

    @InjectMocks private UserService service;

    @Mock private UserRepository repository;

    @Test
    void testGetUserByUsername() {
        // Given
        var user = new AppUser();
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // When
        var retrievedUser = service.getUser("username");
        // Then
        assertNotNull(retrievedUser);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }

    @Test
    void testGetUserById() {
        // Given
        var user = new AppUser();
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));
        // When
        var retrievedUser = service.getUser(5L);
        // Then
        assertNotNull(retrievedUser);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }

    @Test
    void testIsUserInDatabase() {
        // Given
        var user = new AppUser();
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // When
        boolean retrievedAnswer = service.isUserInDatabase("username");
        // Then
        assertTrue(retrievedAnswer);
    }

    @Test
    void testGetUserPass() {
        // Given
        var user = new AppUser();
        user.setPassword("1234");
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // When
        String retrievedPass = service.getUserPass("username");
        // Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
        assertEquals("1234", retrievedPass);
    }

    @Test
    void testSaveUser() {
        // Given
        var user = new AppUser();
        when(repository.save(user)).thenReturn(user);
        // When
        service.saveUser(user);
        // Then
        verify(repository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        // Given
        var user =
                AppUser.builder()
                        .firstName("Oskar")
                        .lastName("Test")
                        .email("testmail@gmail.com")
                        .mobile("656")
                        .username("testuser")
                        .password("123")
                        .role(Role.ROLE_USER)
                        .build();
        var userToUpdate = new AppUser();
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(userToUpdate));
        when(repository.save(userToUpdate)).thenReturn(userToUpdate);
        // When
        service.updateUser(user);
        // Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + user.getUsername()));
        verify(repository, times(1)).save(userToUpdate);
        assertEquals("123", userToUpdate.getPassword());
    }

    @Test
    void testDeleteUser() {
        // Given
        var user = new AppUser();
        user.setId(1L);
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        doNothing().when(repository).deleteById(anyLong());
        // When
        service.deleteUser("username");
        // Then
        verify(repository, times(1)).deleteById(anyLong());
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }
}
