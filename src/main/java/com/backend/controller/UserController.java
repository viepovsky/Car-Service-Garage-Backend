package com.backend.controller;

import com.backend.domain.dto.PasswordDto;
import com.backend.domain.dto.UserDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.facade.UserFacade;
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
    private final UserFacade userFacade;

    @GetMapping(path = "/information")
    public ResponseEntity<UserDto> getUser(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        return ResponseEntity.ok(userFacade.getUserByUsername(username));
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserToLogin(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        return ResponseEntity.ok(userFacade.getUserByUsernameToLogin(username));
    }

    @GetMapping(path = "/is-registered")
    public ResponseEntity<Boolean> isUserRegistered(@RequestParam @NotBlank String username) {
        return ResponseEntity.ok(userFacade.isUserRegistered(username));
    }

    @GetMapping(path = "/pass")
    public ResponseEntity<PasswordDto> getUserPass(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        return ResponseEntity.ok(userFacade.getUserPass(username));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) {
        userFacade.createUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserDto userDto) throws MyEntityNotFoundException {
        userFacade.updateUser(userDto);
        return ResponseEntity.ok().build();
    }
}
