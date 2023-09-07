package com.example.restfulservice.service;

import com.example.restfulservice.dto.UserDto;
import com.example.restfulservice.model.User;

public interface UserService {

    Iterable<User> findAll();

    Iterable<User> findAllExceptId(Long id);

    User findById(Long id);

    User create(UserDto dto);

    User update(UserDto dto, Long id);

    void delete(Long id);

}
