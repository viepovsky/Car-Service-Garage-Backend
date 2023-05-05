package com.viepovsky.user;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public void deleteUser(String username) throws MyEntityNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username));
        userRepository.deleteById(user.getId());
    }
}
