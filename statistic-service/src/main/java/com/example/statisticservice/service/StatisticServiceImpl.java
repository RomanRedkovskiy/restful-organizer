package com.example.statisticservice.service;

import com.example.statisticservice.dto.CompilationChangeDto;
import com.example.statisticservice.dto.TaskChangeDto;
import com.example.statisticservice.dto.UserChangeDto;
import com.example.statisticservice.model.Statistic;
import com.example.statisticservice.repository.StatisticRepository;
import com.example.statisticservice.config.RabbitMQConfig;
import com.example.statisticservice.util.Status;
import com.example.statisticservice.util.statisticMessagesEnum.CompilationChangeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public Statistic findStatisticById(Long id) {
        return statisticRepository.findStatisticByUserIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User statistic with id " + id + " can't be found"));
    }

    @Override
    public void saveStatistic(Statistic statistic) {
        statisticRepository.save(statistic);
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void consumeUserDtoFromQueue(UserChangeDto userDto) {
        switch (userDto.getMessage()) {
            case REGISTER -> createStatisticById(userDto.getUserId());
            case DELETE -> deleteStatisticById(userDto.getUserId());
        }
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.TASK_QUEUE)
    public void consumeTaskDtoFromQueue(TaskChangeDto taskDto) {
        switch (taskDto.getMessage()) {
            case ADD -> addTask(taskDto.getUserIds(), taskDto.getCurrStatus());
            case EDIT -> editTask(taskDto.getUserIds(), taskDto.getPrevStatus(), taskDto.getCurrStatus());
            case DELETE -> deleteTask(taskDto.getUserIds(), taskDto.getPrevStatus());
        }
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.COMPILATION_QUEUE)
    public void consumeCompilationDtoFromQueue(CompilationChangeDto compilationDto) {
        for (Long userId: compilationDto.getUserIds()){
            processCompilationChangeById(userId, compilationDto.getStatusList(), compilationDto.getMessage());
        }
    }

    @Override
    public void processCompilationChangeById(Long userId, List<Status> statusList, CompilationChangeMessage message) {
       Statistic statistic = findStatisticById(userId);
       for(Status status: statusList){
           switch (message) {
               case ADD -> statistic.incrementTaskCount(status);
               case DELETE -> statistic.decrementTaskCount(status);
           }
       }
       saveStatistic(statistic);
    }

    @Override
    public void createStatisticById(Long id) {
        Statistic userStat = new Statistic(id);
        saveStatistic(userStat);
    }

    @Override
    public void deleteStatisticById(Long id) {
        Statistic statistic = findStatisticById(id);
        statistic.setDeleted(true);
        saveStatistic(statistic);
    }

    public void addTask(Set<Long> userIds, Status currStatus) {
        for (Long id : userIds) {
            Statistic statistic = findStatisticById(id);
            statistic.incrementTaskCount(currStatus);
            saveStatistic(statistic);
        }
    }

    public void editTask(Set<Long> userIds, Status prevStatus, Status currStatus) {
        for (Long id : userIds) {
            Statistic statistic = findStatisticById(id);
            statistic.decrementTaskCount(prevStatus);
            statistic.incrementTaskCount(currStatus);
            saveStatistic(statistic);
        }
    }

    public void deleteTask(Set<Long> userIds, Status prevStatus) {
        for (Long id : userIds) {
            Statistic statistic = findStatisticById(id);
            statistic.decrementTaskCount(prevStatus);
            saveStatistic(statistic);
        }
    }

}
