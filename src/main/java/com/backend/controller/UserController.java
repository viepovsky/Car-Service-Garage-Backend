package com.backend.controller;

import com.backend.config.AdminConfig;
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
    private final AdminConfig adminConfig;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws MyEntityNotFoundException {
        User user = userDbService.getUser(userId);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) throws MyEntityNotFoundException {
        User user = userMapper.mapToUser(userDto);
        userDbService.saveUser(user, userDto.getCustomerId());
        return ResponseEntity.ok().build();
    }
}
