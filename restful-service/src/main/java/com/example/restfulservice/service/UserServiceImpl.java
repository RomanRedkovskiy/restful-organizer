package com.example.restfulservice.service;

import com.example.restfulservice.dto.UserDto;
import com.example.restfulservice.model.User;
import com.example.restfulservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> findAllUsers() {
        return userRepository.findByIsDeleted(false);
    }

    @Override
    public Iterable<UserDto> findAllDtos() {
        return userListToDtoList(findAllUsers());
    }

    @Override
    public Iterable<User> findAllUsersExceptId(Long id) {
        return userRepository.findAllByIdNotAndIsDeleted(id, false);
    }

    @Override
    public Iterable<UserDto> findAllDtosExceptId(Long id) {
        return userListToDtoList(findAllUsersExceptId(id));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " can't be found"));
    }

    @Override
    public UserDto checkUserDataCorrectness(UserDto dto) {
         Optional<User> user = userRepository.findUserByNameAndLoginAndPasswordAndIsDeleted(dto.getName(),
                dto.getLogin(), dto.getPassword(), false);
         if(user.isEmpty()) {
             return entityValidaitionFailure(dto);
         }
         return userToDto(user.get());
    }

    @Override
    public UserDto findDtoById(Long id) {
        return userToDto(findUserById(id));
    }

    @Override
    public UserDto create(UserDto dto) {
        User user = new User();
        if (isUsernameTaken(dto)) {
            return entityValidaitionFailure(dto);
        }
        return saveDtoToUser(dto, user);
    }

    @Override
    public UserDto update(UserDto dto) {
        User user = findUserById(dto.getId());
        return saveDtoToUser(dto, user);
    }

    @Override
    public void delete(Long id) {
        User user = findUserById(id);
        user.setDeleted(true);
        userRepository.save(user);
    }

    boolean isUsernameTaken(UserDto dto) {
        return userRepository.findUserByNameAndIsDeleted(dto.getName(), false).isPresent();
    }

    UserDto entityValidaitionFailure(UserDto dto) {
        dto.setId((long) -1);
        return dto;
    }

    Iterable<UserDto> userListToDtoList(Iterable<User> taskList) {
        // create a stream from the source iterable
        return StreamSupport.stream(taskList.spliterator(), false)
                .map(this::userToDto) // apply method to each task
                .collect(Collectors.toList()); // collect the result into a list (or any other collection)
    }

    public UserDto userToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getPassword()
        );
    }

    public UserDto saveDtoToUser(UserDto dto, User user) {
        user.setName(dto.getName());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        userRepository.save(user);
        return userToDto(user);
    }
}
