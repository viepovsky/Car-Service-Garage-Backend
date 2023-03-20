package com.backend.controller;

import com.backend.domain.User;
import com.backend.domain.dto.PasswordDto;
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
import javax.validation.constraints.NotBlank;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserDbService userDbService;
    private final UserMapper userMapper;

    @GetMapping(path = "/information")
    public ResponseEntity<UserDto> getUser(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        User user = userDbService.getUser(username);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserToLogin(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        User user = userDbService.getUser(username);
        return ResponseEntity.ok(userMapper.mapToUserDtoLogin(user));
    }
    @GetMapping(path = "/is-registered")
    public ResponseEntity<Boolean> isUserRegistered(@RequestParam @NotBlank String username) {
        return ResponseEntity.ok(userDbService.isUserInDatabase(username));
    }

    @GetMapping(path = "/pass")
    public ResponseEntity<PasswordDto> getUserPass(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        PasswordDto passwordDto = new PasswordDto(userDbService.getUserPass(username));
        return ResponseEntity.ok(passwordDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) {
        userDbService.saveUser(userMapper.mapToUserLogin(userDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserDto userDto) throws MyEntityNotFoundException {
        userDbService.updateUser(userMapper.mapToUserLogin(userDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws MyEntityNotFoundException {
        userDbService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
