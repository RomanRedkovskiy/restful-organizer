package com.example.userservice.service;

import com.example.userservice.config.RabbitMQConfig;
import com.example.userservice.dto.UserForStatisticDto;
import com.example.userservice.dto.UserForTaskDto;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.UserChangeMessage;
import com.example.userservice.util.jwt.JwtHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final String taskServiceAddress = "http://localhost:8080/users";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User statistic with id " + id + " can't be found"));
    }

    @Override
    public User create(User user) {
        if(isUsernameTaken(user)){
            return entityValidationFailure(user);
        } else {
            saveUser(user);
            sendRegisteredUserDataToTaskService(user);
            sendRegisteredUserDataToMessageBroker(user);
            return user;
        }
    }

    @Override
    public User checkUserDataCorrectness(User dto) {
        Optional<User> user = userRepository.findUserByNameAndLoginAndPasswordAndIsDeleted(dto.getName(),
                dto.getLogin(), dto.getPassword(), false);
        return user.orElseGet(() -> entityValidationFailure(dto));
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = findUserById(id);
        user.setDeleted(true);
        sendDeletedUserDataToTaskService(user.getId());
        sendDeletedUserDataToMessageBroker(user.getId());
        saveUser(user);
    }

    public void sendRegisteredUserDataToTaskService(User user){
        UserForTaskDto userDtoForTask = new UserForTaskDto(user.getId(), user.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", JwtHandler.generateAuthorizationHeader());

        HttpEntity<UserForTaskDto> entity = new HttpEntity<>(userDtoForTask, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(taskServiceAddress, entity, UserForTaskDto.class);
    }

    public void sendDeletedUserDataToTaskService(Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", JwtHandler.generateAuthorizationHeader());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(taskServiceAddress + "/" + id, HttpMethod.DELETE, entity, String.class);
    }

    public void sendRegisteredUserDataToMessageBroker(User user){
        UserForStatisticDto userForStatisticDto = new UserForStatisticDto(user.getId(), UserChangeMessage.REGISTER);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", JwtHandler.generateAuthorizationHeader());

        HttpEntity<UserForStatisticDto> entity = new HttpEntity<>(userForStatisticDto, headers);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.USER_ROUTING_KEY, entity);
    }

    public void sendDeletedUserDataToMessageBroker(Long id){
        UserForStatisticDto userForStatisticDto = new UserForStatisticDto(id, UserChangeMessage.DELETE);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", JwtHandler.generateAuthorizationHeader());

        HttpEntity<UserForStatisticDto> entity = new HttpEntity<>(userForStatisticDto, headers);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.USER_ROUTING_KEY, entity);
    }

    boolean isUsernameTaken(User user) {
        return userRepository.findUserByNameAndIsDeleted(user.getName(), false).isPresent();
    }

    User entityValidationFailure(User user) {
        user.setId((long) -1);
        return user;
    }
}
