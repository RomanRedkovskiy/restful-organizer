package com.example.userservice.service;

import com.example.userservice.config.RabbitMQConfig;
import com.example.userservice.dto.UserForStatisticDto;
import com.example.userservice.dto.UserForTaskDto;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.UserChangeMessage;
import com.example.userservice.util.jwt.Role;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final String taskServiceAddress = "http://localhost:8080/users";

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserByIdAndIsDeleted(id, false).orElseThrow(() ->
                new EntityNotFoundException("User with id " + id + " can't be found"));
    }


    @Override
    public User checkUserDataCorrectness(User dto) {
        Optional<User> user = userRepository.findUserByNameAndLoginAndPasswordAndIsDeleted(dto.getName(),
                dto.getLogin(), dto.getPassword(), false);
        return user.orElseGet(() -> entityValidationFailure(dto));
    }


    @Override
    public User create(User user) {
        return addUserIfUniqueUsername(user);
    }

    @Override
    public User update(User user) {
        return changeUserIfUniqueUsername(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = findUserById(id);
        user.setDeleted(true);
        sendDeletedUserDataToTaskService(user.getId());
        sendUserDataToMessageBroker(user, UserChangeMessage.DELETE);
        saveUser(user);
    }

    private boolean isUsernameTaken(User user) {
        return userRepository.findUserByNameAndIsDeleted(user.getName(), false).isPresent();
    }

    private void saveUser(User user) {
        userRepository.save(user);
    }

    private User addUserIfUniqueUsername(User user) {
        if (isUsernameTaken(user)) {
            return entityValidationFailure(user);
        }
        saveUser(user);
        sendUserDataToTaskService(user, HttpMethod.POST);
        sendUserDataToMessageBroker(user, UserChangeMessage.REGISTER);
        return user;
    }

    private User changeUserIfUniqueUsername(User user) {
        boolean wasNicknameChanged = !findUserById(user.getId()).getName().equals(user.getName());
        if (wasNicknameChanged && isUsernameTaken(user)) {
            return entityValidationFailure(user);
        }
        saveUser(user);
        if (wasNicknameChanged) {
            sendUserDataToTaskService(user, HttpMethod.PUT);
            sendUserDataToMessageBroker(user, UserChangeMessage.CHANGE);
        }
        return user;
    }

    private void sendUserDataToTaskService(User user, HttpMethod httpMethod) {
        UserForTaskDto userDtoForTask = new UserForTaskDto(user.getId(), user.getName());
        HttpEntity<UserForTaskDto> entity = new HttpEntity<>(userDtoForTask, constructAuthorizationHeader());
        sendDataToTaskService(taskServiceAddress, httpMethod, entity, UserForTaskDto.class);
    }

    private void sendDeletedUserDataToTaskService(Long id) {
        HttpEntity<String> entity = new HttpEntity<>(constructAuthorizationHeader());
        sendDataToTaskService(taskServiceAddress + "/" + id, HttpMethod.DELETE, entity, String.class);
    }

    private void sendUserDataToMessageBroker(User user, UserChangeMessage changeMessage) {
        UserForStatisticDto userForStatisticDto =
                new UserForStatisticDto(jwtService.generateAuthorizationHeader(Role.USER),user.getId(), user.getName(),
                        changeMessage);
        sendDataToMessageBroker(userForStatisticDto);
    }

    private void sendDataToTaskService(String url, HttpMethod method, HttpEntity<?> entity, Class<?> className) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(url, method, entity, className);
    }

    private void sendDataToMessageBroker(UserForStatisticDto message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.USER_ROUTING_KEY, message);
    }

    private HttpHeaders constructAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtService.generateAuthorizationHeader(Role.USER));
        return headers;
    }

    private User entityValidationFailure(User user) {
        user.setId((long) -1);
        return user;
    }
}
