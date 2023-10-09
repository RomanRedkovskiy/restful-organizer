package com.example.taskservice.service;

import com.example.taskservice.dto.UserDto;
import com.example.taskservice.model.User;

public interface UserService {

    Iterable<User> findAllUsers();

    Iterable<UserDto> findAllDtos();

    Iterable<User> findAllUsersExceptId(Long id);

    Iterable<UserDto> findAllDtosExceptId(Long id);

    User findUserById(Long id);

    UserDto checkUserDataCorrectness(UserDto dto);

    UserDto findDtoById(Long id);

    UserDto create(UserDto dto);

    UserDto update(UserDto dto);

    void delete(Long id);

}
