package com.viepovsky.user;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new MyEntityNotFoundException("Username: " + username));
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("Username: " + id));
    }

    public Boolean isUserInDatabase(String username) {
        return userRepository.findByUsername(username)
                .isPresent();
    }

    public String getUserPass(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new MyEntityNotFoundException("Username: " + username))
                .getPassword();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void updateUser(User user) {
        User userToUpdate = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new MyEntityNotFoundException("Username: " + user.getUsername()));
        userToUpdate.updateUser(user);
        userRepository.save(userToUpdate);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new MyEntityNotFoundException("Username: " + username));
        userRepository.deleteById(user.getId());
    }
}
