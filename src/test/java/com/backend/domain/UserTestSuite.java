package com.backend.domain;

import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.service.UserDbService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@DisplayName("User Entity Test Suite")
class UserTestSuite {

    @Autowired
    private UserDbService userDbService;

    @Test
    public void testSaveAndRetrieveUser() {
        //Given
        User user = new User("Oskar", "Raj", "testmail@gmail.com", "+48756756756", "testusername", "testpassword", UserRole.USER, LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());
        userDbService.saveUser(user);
        //When
        List<User> userList = userDbService.getAllUsers();
        User retrievedUser = userList.get(0);
        //Then
        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getCreatedDate(), retrievedUser.getCreatedDate());
        assertDoesNotThrow(() -> userDbService.getUser(retrievedUser.getId()));
        System.out.println(retrievedUser.getId());
    }

    @Test
    public void testUpdateAndDeleteUser() throws MyEntityNotFoundException {
        //Given
        User user = new User("Oskar", "Raj", "testmail@gmail.com", "+48756756756", "testusername", "testpassword", UserRole.USER, LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());
        userDbService.saveUser(user);
        List<User> userList = userDbService.getAllUsers();
        User userToUpdate = userList.get(0);
        userToUpdate.setFirstName("Wiktor");
        userToUpdate.setEmail("tested@gmail.com");
        //When
        userDbService.updateUser(userToUpdate);
        User updatedUser = userDbService.getUser(userToUpdate.getUsername());
        userDbService.deleteUser(updatedUser.getId());
        //Then
        assertEquals(userToUpdate.getId(), updatedUser.getId());
        assertEquals("Wiktor", updatedUser.getFirstName());
        assertEquals("tested@gmail.com", updatedUser.getEmail());
        assertThrows(MyEntityNotFoundException.class, () -> userDbService.getUser(updatedUser.getId()));
        System.out.println(updatedUser.getId());
    }

    @Test
    public void testThrowsMyEntityNotFoundException() {
        //Given & When & Then
        MyEntityNotFoundException exception = assertThrows(MyEntityNotFoundException.class, () -> userDbService.getUser(1L));
        assertEquals("User of id: 1", exception.getMessage());
        assertEquals(1L, exception.getRecordId());
    }
}