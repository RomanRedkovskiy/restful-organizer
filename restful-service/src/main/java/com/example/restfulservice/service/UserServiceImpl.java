package com.example.restfulservice.service;

import com.example.restfulservice.dto.UserDto;
import com.example.restfulservice.model.User;
import com.example.restfulservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAllByIsDeleted(false);
    }

    @Override
    public Iterable<User> findAllExceptId(Long id) {
        return userRepository.findAllByIdNotAndIsDeleted(id, false);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findUserByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " can't be found"));
    }

    @Override
    public User create(UserDto dto) {
        User user = new User();
        return saveDtoToUser(dto, user);
    }

    @Override
    public User update(UserDto dto, Long id) {
        User user = findById(id);
        return saveDtoToUser(dto, user);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        user.setDeleted(true);
        userRepository.save(user);
    }

    public User saveDtoToUser(UserDto dto, User user) {
        user.setName(dto.getName());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        userRepository.save(user);
        return user;
    }
}
