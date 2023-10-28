package com.example.userservice.service;

import com.example.userservice.model.User;

public interface UserService {

    User findUserById(Long id);

    User create(User user);

    boolean checkUserDataCorrectness(User dto);

    void saveUser(User user);

    void deleteUserById(Long id);

}
