package com.example.statisticservice.service;

import com.example.statisticservice.dto.CompilationChangeDto;
import com.example.statisticservice.dto.TaskChangeDto;
import com.example.statisticservice.dto.UserChangeDto;
import com.example.statisticservice.model.Statistic;
import com.example.statisticservice.util.Status;
import com.example.statisticservice.util.statisticMessagesEnum.CompilationChangeMessage;

import java.util.List;
import java.util.Set;

public interface StatisticService {

    Statistic findStatisticById(Long id);

    void saveStatistic(Statistic statistic);

    void createStatisticById(Long id);

    void deleteStatisticById(Long id);

    void addTask(Set<Long> userId, Status currStatus);

    void editTask(Set<Long> userId, Status prevStatus, Status currStatus);

    void deleteTask(Set<Long> userId, Status prevStatus);

    void processCompilationChangeById(Long userId, List<Status> statusList, CompilationChangeMessage message);

    //methods to consume data from RabbitMQ
    void consumeUserDtoFromQueue(UserChangeDto userDto);

    void consumeTaskDtoFromQueue(TaskChangeDto taskDto);

    void consumeCompilationDtoFromQueue(CompilationChangeDto compilationDto);


}
