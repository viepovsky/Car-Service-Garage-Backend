package com.backend.domain;

import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.scheduler.ApplicationScheduler;
import com.backend.service.UserDbService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@MockBean(ApplicationScheduler.class)
@Transactional
@DisplayName("User Entity Test Suite")
class UserTestSuite {

    @Autowired
    private UserDbService userDbService;

    @Test
    public void testSaveAndRetrieveUser() throws MyEntityNotFoundException {
        //Given
        User user = new User("Oskar", "Test", "testmail@gmail.com", "+48756756756", "testusername", "testpassword", UserRole.USER, LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());
        userDbService.saveUser(user);
        //When
        User retrievedUser = userDbService.getUser("testusername");
        //Then
        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getCreatedDate(), retrievedUser.getCreatedDate());
        assertDoesNotThrow(() -> userDbService.getUser(retrievedUser.getUsername()));
        System.out.println(retrievedUser.getId());
    }

    @Test
    public void testUpdateAndDeleteUser() throws MyEntityNotFoundException {
        //Given
        User user = new User("Oskar", "Test", "testmail@gmail.com", "+48756756756", "testusername", "testpassword", UserRole.USER, LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());
        userDbService.saveUser(user);
        User userToUpdate = userDbService.getUser("testusername");
        userToUpdate.setFirstName("Wiktor");
        userToUpdate.setEmail("tested@gmail.com");
        //When
        userDbService.updateUser(userToUpdate);
        User updatedUser = userDbService.getUser(userToUpdate.getUsername());
        userDbService.deleteUser("testusername");
        //Then
        assertEquals(userToUpdate.getId(), updatedUser.getId());
        assertEquals("Wiktor", updatedUser.getFirstName());
        assertEquals("tested@gmail.com", updatedUser.getEmail());
        assertThrows(MyEntityNotFoundException.class, () -> userDbService.getUser(updatedUser.getUsername()));
        System.out.println(updatedUser.getId());
    }

    @Test
    public void testThrowsMyEntityNotFoundException() {
        //Given & When & Then
        MyEntityNotFoundException exception = assertThrows(MyEntityNotFoundException.class, () -> userDbService.getUser("testusername"));
        assertEquals("Username: testusername", exception.getMessage());
        assertNull(exception.getRecordId());
    }
}