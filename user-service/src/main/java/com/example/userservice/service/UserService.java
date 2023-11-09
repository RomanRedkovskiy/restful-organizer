package com.example.userservice.service;

import com.example.userservice.model.User;

public interface UserService {

    User findUserById(Long id);

    User checkUserDataCorrectness(User dto);

    User create(User user);

    User update(User user);

    void deleteUserById(Long id);
}
