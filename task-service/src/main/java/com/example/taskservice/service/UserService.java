package com.example.taskservice.service;

import com.example.taskservice.dto.UserDto;
import com.example.taskservice.model.User;

public interface UserService {

    UserDto create(UserDto dto);

    void delete(Long id);

    User findUserById(Long id);

    Iterable<User> findAllUsers();

    Iterable<UserDto> findAllUserDtos();

    Iterable<User> findAllUsersExceptId(Long id);

    Iterable<UserDto> findAllDtosExceptId(Long id);

    UserDto findUserDtoById(Long id);

    UserDto update(UserDto dto);

}
