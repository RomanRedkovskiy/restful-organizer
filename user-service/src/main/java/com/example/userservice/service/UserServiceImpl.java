package com.example.userservice.service;

import com.example.userservice.config.RabbitMQConfig;
import com.example.userservice.dto.UserForStatisticDto;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.UserChangeMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

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
        saveUser(user);
        sendUserDataToTaskService(user);
        sendRegisteredUserDataToMessageBroker(user);
        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = findUserById(id);
        user.setDeleted(true);
        sendDeletedUserDataToMessageBroker(user);
        saveUser(user);
    }

    public void sendUserDataToTaskService(User user){
/*        UserDtoForTask userDtoForTask = new UserDtoForTask(user.getId(), user.getName());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.postForEntity("http://localhost:8080/users", userDtoForTask, UserDtoForTask.class);*/
    }

    public void sendRegisteredUserDataToMessageBroker(User user){
        UserForStatisticDto userForStatisticDto = new UserForStatisticDto(user.getId(), UserChangeMessage.REGISTER);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.USER_ROUTING_KEY, userForStatisticDto);
    }

    public void sendDeletedUserDataToMessageBroker(User user){
        UserForStatisticDto userForStatisticDto = new UserForStatisticDto(user.getId(), UserChangeMessage.DELETE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.USER_ROUTING_KEY, userForStatisticDto);
    }
}
