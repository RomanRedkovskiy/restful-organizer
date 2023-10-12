package com.example.userservice.service;

import com.example.userservice.config.RabbitMQConfig;
import com.example.userservice.dto.UserDtoForStatistic;
import com.example.userservice.dto.UserDtoForTask;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public User create(User user) {
        sendUserDataToTaskService(user);
        sendUserDataToMessageBroker(user);
        return user;
    }

    public void sendUserDataToTaskService(User user){
        UserDtoForTask userDtoForTask = new UserDtoForTask(user.getId(), user.getName());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.postForEntity("http://localhost:8080/users", userDtoForTask, UserDtoForTask.class);
    }

    public void sendUserDataToMessageBroker(User user){
        UserDtoForStatistic userDtoForStatistic = new UserDtoForStatistic(user.getId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.TASK_ROUTING_KEY, userDtoForStatistic);
    }
}
