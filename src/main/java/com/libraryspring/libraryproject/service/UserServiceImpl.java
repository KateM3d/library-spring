package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.UserDto;
import com.libraryspring.libraryproject.model.User;
import com.libraryspring.libraryproject.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserDto> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userOptional.get(), userDto);
            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        userDto.setId(user.getId());
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found with id: " + id);
        }

        User existingUser = optionalUser.get();
        existingUser.setRole(userDto.getRole());
        existingUser.setPassword(userDto.getPassword());

        User updatedUser = userRepository.save(existingUser);

        return UserDto.builder()
                .id(updatedUser.getId())
                .role(updatedUser.getRole())
                .password(updatedUser.getPassword())
                .build();
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}