package com.backend.controller;

import com.backend.domain.User;
import com.backend.domain.dto.UserDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.UserMapper;
import com.backend.service.UserDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserDbService userDbService;
    private final UserMapper userMapper;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws MyEntityNotFoundException {
        User user = userDbService.getUser(userId);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @GetMapping
    public ResponseEntity<UserDto> getUser(@RequestParam String username) throws MyEntityNotFoundException {
        User user = userDbService.getUser(username);
        return ResponseEntity.ok(userMapper.mapToUserDtoLogin(user));
    }
    @GetMapping(path = "/is-registered")
    public ResponseEntity<Boolean> isUserRegistered(@RequestParam String username) {
        return ResponseEntity.ok(userDbService.isUserInDatabase(username));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) {
        userDbService.saveUser(userMapper.mapToUserLogin(userDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws MyEntityNotFoundException {
        userDbService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
