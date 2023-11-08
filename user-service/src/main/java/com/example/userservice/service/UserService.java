package com.example.userservice.service;

import com.example.userservice.model.User;

public interface UserService {

    User findUserById(Long id);

    User create(User user);

    User checkUserDataCorrectness(User dto);

    User update(User user);

    User changeUserIfUniqueUsername(User user);

    User addUserIfUniqueUsername(User user);

    void saveUser(User user);

    void deleteUserById(Long id);

}
