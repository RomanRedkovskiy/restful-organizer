package com.example.statisticservice.service;

import com.example.statisticservice.dto.StatisticDto;
import com.example.statisticservice.dto.messageBrokerDtos.CompilationChangeDto;
import com.example.statisticservice.dto.messageBrokerDtos.TaskChangeDto;
import com.example.statisticservice.dto.messageBrokerDtos.UserChangeDto;
import org.springframework.http.HttpEntity;

public interface StatisticService {

    StatisticDto getStatisticDto(Long id);

    Iterable<StatisticDto> getStatisticDtos();

    //methods to consume data from RabbitMQ
    void consumeUserDtoFromQueue(HttpEntity<UserChangeDto> entity);

    void consumeTaskDtoFromQueue(HttpEntity<TaskChangeDto> entity);

    void consumeCompilationDtoFromQueue(HttpEntity<CompilationChangeDto> entity);


}
