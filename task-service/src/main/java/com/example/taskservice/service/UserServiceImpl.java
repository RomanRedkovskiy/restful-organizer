package com.example.taskservice.service;

import com.example.taskservice.config.RabbitMQConfig;
import com.example.taskservice.dto.UserDto;
import com.example.taskservice.model.User;
import com.example.taskservice.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " can't be found"));
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

    public UserDto saveDtoToUser(UserDto dto, User user) {
        user.setName(dto.getName());
        user.setId(dto.getId());
        userRepository.save(user);
        return userToDto(user);
    }

    public UserDto userToDto(User user) {
        return new UserDto(user.getId(), user.getName());
    }


    @Override
    public Iterable<User> findAllUsers() {
        return userRepository.findByIsDeleted(false);
    }

    @Override
    public Iterable<UserDto> findAllUserDtos() {
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

/*    @Override
    public UserDto checkUserDataCorrectness(UserDto dto) {
         Optional<User> user = userRepository.findUserByNameAndLoginAndPasswordAndIsDeleted(dto.getName(),
                dto.getLogin(), dto.getPassword(), false);
         if(user.isEmpty()) {
             return entityValidationFailure(dto);
         }
         return userToDto(user.get());
    }*/

    @Override
    public UserDto findUserDtoById(Long id) {
        return userToDto(findUserById(id));
    }

    @Override
    public UserDto update(UserDto dto) {
        User user = findUserById(dto.getId());
        return saveDtoToUser(dto, user);
    }

    Iterable<UserDto> userListToDtoList(Iterable<User> taskList) {
        // create a stream from the source iterable
        return StreamSupport.stream(taskList.spliterator(), false)
                .map(this::userToDto) // apply method to each task
                .collect(Collectors.toList()); // collect the result into a list (or any other collection)
    }
}
