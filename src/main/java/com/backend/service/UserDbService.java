package com.backend.service;

import com.backend.domain.User;
import com.backend.domain.UserRole;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDbService {
    private final UserRepository userRepository;

    public User getUser(String username) throws MyEntityNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username));
    }

    public Boolean isUserInDatabase(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public String getUserPass(String username) throws MyEntityNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username)).getPassword();
    }

    public void saveUser(User user) {
        if (user.getCreatedDate() == null) {
            user.setCreatedDate(LocalDateTime.now());
        }
        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }
        userRepository.save(user);
    }

    public void updateUser(User user) throws MyEntityNotFoundException {
        User userToUpdate = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new MyEntityNotFoundException("Username: " + user.getUsername()));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        if (user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
            userRepository.save(userToUpdate);
        } else {
            userRepository.save(userToUpdate);
        }
    }

    public void deleteUser(Long userId) throws MyEntityNotFoundException {
        if(userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new MyEntityNotFoundException("User", userId);
        }
    }
}
