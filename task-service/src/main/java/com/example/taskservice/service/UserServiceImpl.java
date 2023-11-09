package com.example.taskservice.service;

import com.example.taskservice.dto.UserDto;
import com.example.taskservice.model.User;
import com.example.taskservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " can't be found"));
    }

    @Override
    public String getUserNameById(Long id) {
        return findUserById(id).getName();
    }

    @Override
    public UserDto create(UserDto dto) {
        return saveDtoToUser(dto, new User());
    }

    @Override
    public void delete(Long id) {
        User user = findUserById(id);
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public Iterable<UserDto> findAllUserDtos() {
        return userListToDtoList(findAllUsers());
    }

    @Override
    public Iterable<UserDto> findAllDtosExceptId(Long id) {
        return userListToDtoList(findAllUsersExceptId(id));
    }

    @Override
    public UserDto findUserDtoById(Long id) {
        return userToDto(findUserById(id));
    }

    @Override
    public boolean isAdmin(Long id) {
        User user = findUserById(id);
        return user.isAdmin();
    }

    @Override
    public UserDto update(UserDto dto) {
        User user = findUserById(dto.getId());
        return saveDtoToUser(dto, user);
    }

    private UserDto saveDtoToUser(UserDto dto, User user) {
        user.setName(dto.getName());
        user.setId(dto.getId());
        userRepository.save(user);
        return userToDto(user);
    }

    private UserDto userToDto(User user) {
        return new UserDto(user.getId(), user.getName());
    }

    private Iterable<User> findAllUsers() {
        return userRepository.findByIsDeleted(false);
    }

    private Iterable<User> findAllUsersExceptId(Long id) {
        return userRepository.findAllByIdNotAndIsDeleted(id, false);
    }

    private Iterable<UserDto> userListToDtoList(Iterable<User> taskList) {
        // create a stream from the source iterable
        return StreamSupport.stream(taskList.spliterator(), false)
                .map(this::userToDto) // apply method to each task
                .collect(Collectors.toList()); // collect the result into a list (or any other collection)
    }
}
