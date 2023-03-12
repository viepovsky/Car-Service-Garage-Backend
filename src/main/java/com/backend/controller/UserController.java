package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/usersssssss")
@RequiredArgsConstructor
@Validated
public class UserController {

//    @GetMapping(path = "/{userId}")
//    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws MyEntityNotFoundException {
//        User user = userDbService.getUser(userId);
//        return ResponseEntity.ok(userMapper.mapToUserDto(user));
//    }
//



//    @GetMapping
//    public ResponseEntity<UserDto> getUser(@Valid @RequestParam(name = "username") @NotBlank String username) throws MyEntityNotFoundException {
//        User user = userDbService.getUser(username);
//        return ResponseEntity.ok(userMapper.mapToUserDtoTest(user));
//    }
//
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) throws MyEntityNotFoundException {
//        User user = userMapper.mapToUser(userDto);
//        userDbService.saveUser(user);
////        userDbService.saveUser(user, userDto.getCustomerId());
//        return ResponseEntity.ok().build();
//    }
}
