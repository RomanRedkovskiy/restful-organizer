package com.example.statisticservice.service;

import com.example.statisticservice.dto.StatisticDto;
import com.example.statisticservice.dto.messageBrokerDtos.CompilationChangeDto;
import com.example.statisticservice.dto.messageBrokerDtos.TaskChangeDto;
import com.example.statisticservice.dto.messageBrokerDtos.UserChangeDto;
import com.example.statisticservice.model.Statistic;
import com.example.statisticservice.util.Status;
import com.example.statisticservice.util.statisticMessagesEnum.CompilationChangeMessage;
import org.springframework.http.HttpEntity;

import java.util.List;
import java.util.Set;

public interface StatisticService {

    Statistic findStatisticById(Long id);

    void saveStatistic(Statistic statistic);

    void createStatisticById(UserChangeDto dto);

    void changeStatisticById(UserChangeDto dto);

    void deleteStatisticById(Long id);

    void addTask(Set<Long> userId, Status currStatus);

    void editTask(Set<Long> userId, Status prevStatus, Status currStatus);

    void deleteTask(Set<Long> userId, Status prevStatus);

    void processCompilationChangeById(Long userId, List<Status> statusList, CompilationChangeMessage message);

    StatisticDto getStatisticDto(Long id);

    StatisticDto statToStatDto(Statistic statistic);

    Iterable<StatisticDto> getStatisticDtos();

    Iterable<StatisticDto> statListToStatDtoList(Iterable<Statistic> statList);

    //methods to consume data from RabbitMQ
    void consumeUserDtoFromQueue(HttpEntity<UserChangeDto> entity);

    void consumeTaskDtoFromQueue(HttpEntity<TaskChangeDto> entity);

    void consumeCompilationDtoFromQueue(HttpEntity<CompilationChangeDto> entity);


}
