package com.viepovsky.user;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.scheduler.ApplicationScheduler;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
@Transactional
@DisplayName("User Entity Test Suite")
class UserTestSuite {

    @Autowired
    private UserService service;

    @Test
    public void testSaveAndRetrieveUser() throws MyEntityNotFoundException {
        //Given
        User user = new User("Oskar", "Test", "testmail@gmail.com", "+48756756756", "testusername", "testpassword", Role.ROLE_USER, new ArrayList<>(), new ArrayList<>());
        service.saveUser(user);
        //When
        User retrievedUser = service.getUser("testusername");
        //Then
        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getCreatedDate(), retrievedUser.getCreatedDate());
        assertDoesNotThrow(() -> service.getUser(retrievedUser.getUsername()));
        System.out.println(retrievedUser.getId());
    }

    @Test
    public void testUpdateAndDeleteUser() throws MyEntityNotFoundException {
        //Given
        User user = new User("Oskar", "Test", "testmail@gmail.com", "+48756756756", "testusername", "testpassword", Role.ROLE_USER, new ArrayList<>(), new ArrayList<>());
        service.saveUser(user);
        User userToUpdate = service.getUser("testusername");
        userToUpdate.setFirstName("Wiktor");
        userToUpdate.setEmail("tested@gmail.com");
        //When
        service.updateUser(userToUpdate);
        User updatedUser = service.getUser(userToUpdate.getUsername());
        service.deleteUser("testusername");
        //Then
        assertEquals(userToUpdate.getId(), updatedUser.getId());
        assertEquals("Wiktor", updatedUser.getFirstName());
        assertEquals("tested@gmail.com", updatedUser.getEmail());
        assertThrows(MyEntityNotFoundException.class, () -> service.getUser(updatedUser.getUsername()));
        System.out.println(updatedUser.getId());
    }

    @Test
    public void testThrowsMyEntityNotFoundException() {
        //Given & When & Then
        MyEntityNotFoundException exception = assertThrows(MyEntityNotFoundException.class, () -> service.getUser("testusername"));
        assertEquals("Username: testusername", exception.getMessage());
        assertNull(exception.getRecordId());
    }
}