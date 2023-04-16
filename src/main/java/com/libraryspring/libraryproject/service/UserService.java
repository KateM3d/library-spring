package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.UserDto;
import com.libraryspring.libraryproject.model.User;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> getUserById(Long id);

    Optional<User> getUserByRole(String role);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}