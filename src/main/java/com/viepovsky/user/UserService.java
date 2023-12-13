package com.viepovsky.user;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User getUser(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username));
    }

    public User getUser(Long id) {
        return repository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Username: " + id));
    }

    public Boolean isUserInDatabase(String username) {
        return repository.findByUsername(username).isPresent();
    }

    public String getUserPass(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username)).getPassword();
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public void updateUser(User user) {
        User userToUpdate = repository.findByUsername(user.getUsername()).orElseThrow(() -> new MyEntityNotFoundException("Username: " + user.getUsername()));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        if (user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
            repository.save(userToUpdate);
        } else {
            repository.save(userToUpdate);
        }
    }

    public void deleteUser(String username) {
        User user = repository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username));
        repository.deleteById(user.getId());
    }
}
