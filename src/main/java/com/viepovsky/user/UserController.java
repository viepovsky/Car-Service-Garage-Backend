package com.viepovsky.user;

import com.viepovsky.user.dto.PasswordDto;
import com.viepovsky.user.dto.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Validated
class UserController {
    private final UserFacade userFacade;

    @GetMapping(path = "/information")
    ResponseEntity<UserDto> getUser(@RequestParam @NotBlank String username) {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!usernameFromToken.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userFacade.getUserByUsername(username));
    }

    @GetMapping(path = "/login")
    ResponseEntity<UserDto> getUserToLogin(@RequestParam @NotBlank String username) {
        return ResponseEntity.ok(userFacade.getUserByUsernameToLogin(username));
    }

    @GetMapping(path = "/is-registered")
    ResponseEntity<Boolean> isUserRegistered(@RequestParam @NotBlank String username) {
        return ResponseEntity.ok(userFacade.isUserRegistered(username));
    }

    @GetMapping(path = "/pass")
    ResponseEntity<PasswordDto> getUserPass(@RequestParam @NotBlank String username) {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!usernameFromToken.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userFacade.getUserPass(username));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) {
        var createdUser = userFacade.createUser(userDto);
        return ResponseEntity.created(URI.create("/v1/users/information?username=" + createdUser.getUsername())).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateUser(@Valid @RequestBody UserDto userDto) {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!usernameFromToken.equals(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userFacade.updateUser(userDto);
        return ResponseEntity.noContent().build();
    }
}
