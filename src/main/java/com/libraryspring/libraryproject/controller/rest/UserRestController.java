package com.libraryspring.libraryproject.controller.rest;

import com.libraryspring.libraryproject.dto.UserDto;
import com.libraryspring.libraryproject.model.User;
import com.libraryspring.libraryproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<User> getUserByRole(@RequestParam("role") String role) {
        Optional<User> userDto = userService.getUserByRole(role);
        return userDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        Optional<UserDto> userDto = userService.getUserById(id);
        return userDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}