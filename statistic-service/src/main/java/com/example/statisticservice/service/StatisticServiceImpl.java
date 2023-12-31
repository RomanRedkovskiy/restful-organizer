package com.example.statisticservice.service;

import com.example.statisticservice.config.RabbitMQConfig;
import com.example.statisticservice.dto.StatisticDto;
import com.example.statisticservice.dto.messageBrokerDtos.CompilationChangeDto;
import com.example.statisticservice.dto.messageBrokerDtos.TaskChangeDto;
import com.example.statisticservice.dto.messageBrokerDtos.UserChangeDto;
import com.example.statisticservice.model.Statistic;
import com.example.statisticservice.repository.StatisticRepository;
import com.example.statisticservice.util.Consumer;
import com.example.statisticservice.util.Status;
import com.example.statisticservice.util.jwt.JwtData;
import com.example.statisticservice.util.jwt.Role;
import com.example.statisticservice.util.statisticMessagesEnum.CompilationChangeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    private final JwtService jwtService;

    @Autowired
    public StatisticServiceImpl(StatisticRepository statisticRepository, JwtService jwtService) {
        this.statisticRepository = statisticRepository;
        this.jwtService = jwtService;
    }

    private String lastToken;

    @Override
    public StatisticDto getStatisticDto(Long id) {
        Statistic statistic = findStatisticById(id);
        return statToStatDto(statistic);
    }

    @Override
    public Iterable<StatisticDto> getStatisticDtos() {
        Iterable<Statistic> stats = statisticRepository.findAllByIsDeleted(false);
        return statListToStatDtoList(stats);
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void consumeUserDtoFromQueue(HttpEntity<UserChangeDto> entity) {
        processDtoFromQueue(entity, (userDto) -> {
            switch (userDto.getMessage()) {
                case REGISTER -> createStatisticById(userDto);
                case CHANGE -> changeStatisticById(userDto);
                case DELETE -> deleteStatisticById(userDto.getUserId());
            }
        });
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.TASK_QUEUE)
    public void consumeTaskDtoFromQueue(HttpEntity<TaskChangeDto> entity) {
        processDtoFromQueue(entity, (taskDto) -> {
            switch (taskDto.getMessage()) {
                case ADD -> addTask(taskDto.getUserIds(), taskDto.getCurrStatus());
                case EDIT -> editTask(taskDto.getUserIds(), taskDto.getPrevStatus(), taskDto.getCurrStatus());
                case DELETE -> deleteTask(taskDto.getUserIds(), taskDto.getPrevStatus());
            }
        });
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.COMPILATION_QUEUE)
    public void consumeCompilationDtoFromQueue(HttpEntity<CompilationChangeDto> entity) {
        processDtoFromQueue(entity, (compilationDto) -> {
            for (Long userId : compilationDto.getUserIds()) {
                processCompilationChangeById(userId, compilationDto.getStatusList(), compilationDto.getMessage());
            }
        });
    }

    private Statistic findStatisticById(Long id) {
        return statisticRepository.findStatisticByUserIdAndIsDeleted(id, false).orElseThrow(() ->
                new EntityNotFoundException("User statistic with id " + id + " can't be found"));
    }

    private void saveStatistic(Statistic statistic) {
        statisticRepository.save(statistic);
    }

    private void processCompilationChangeById(Long userId, List<Status> statusList, CompilationChangeMessage message) {
        Statistic statistic = findStatisticById(userId);
        for (Status status : statusList) {
            switch (message) {
                case ADD -> statistic.incrementTaskCount(status);
                case DELETE -> statistic.decrementTaskCount(status);
            }
        }
        saveStatistic(statistic);
    }

    private void createStatisticById(UserChangeDto dto) {
        Statistic userStat = new Statistic(dto.getUserId(), dto.getUserName());
        saveStatistic(userStat);
    }

    private void changeStatisticById(UserChangeDto dto) {
        Statistic userStat = findStatisticById(dto.getUserId());
        userStat.setUserName(dto.getUserName());
        saveStatistic(userStat);
    }

    private void deleteStatisticById(Long id) {
        Statistic statistic = findStatisticById(id);
        statistic.setDeleted(true);
        saveStatistic(statistic);
    }

    private Iterable<StatisticDto> statListToStatDtoList(Iterable<Statistic> statList) {
        return StreamSupport.stream(statList.spliterator(), false)
                .map(this::statToStatDto) // apply method to each task
                .collect(Collectors.toList()); // collect the result into a list
    }

    private StatisticDto statToStatDto(Statistic statistic) {
        int completedTasks = statistic.getCompletedTasks();
        int inProgressTasks = statistic.getInProgressTasks();
        int uncompletedTasks = statistic.getUncompletedTasks();
        int overallTasks = completedTasks + inProgressTasks + uncompletedTasks;
        float taskValue = 100f / overallTasks;
        int completeness = (int) (taskValue * (completedTasks + inProgressTasks / 2f));
        return new StatisticDto(statistic.getUserName(), completedTasks, inProgressTasks,
                uncompletedTasks, overallTasks, completeness);
    }

    private void addTask(Set<Long> userIds, Status currStatus) {
        for (Long id : userIds) {
            Statistic statistic = findStatisticById(id);
            statistic.incrementTaskCount(currStatus);
            saveStatistic(statistic);
        }
    }

    private void editTask(Set<Long> userIds, Status prevStatus, Status currStatus) {
        for (Long id : userIds) {
            Statistic statistic = findStatisticById(id);
            statistic.decrementTaskCount(prevStatus);
            statistic.incrementTaskCount(currStatus);
            saveStatistic(statistic);
        }
    }

    private void deleteTask(Set<Long> userIds, Status prevStatus) {
        for (Long id : userIds) {
            Statistic statistic = findStatisticById(id);
            statistic.decrementTaskCount(prevStatus);
            saveStatistic(statistic);
        }
    }

    private <T> void processDtoFromQueue(HttpEntity<T> entity, Consumer<T> dtoConsumer) {
        Optional<String> optionalToken = Optional.ofNullable(entity.getHeaders().getFirst("Authorization"));
        Optional<T> optionalDto = Optional.ofNullable(entity.getBody());
        String token;
        T dto;
        if (optionalToken.isEmpty() || optionalDto.isEmpty()) {
            return;
        }
        token = optionalToken.get();
        dto = optionalDto.get();
        if (token.equals(lastToken)) {
            return;
        }
        lastToken = token;
        Optional<JwtData> optionalJwtData = jwtService.parseHeader(token);
        if (optionalJwtData.isEmpty()) {
            return;
        }
        JwtData jwtData = optionalJwtData.get();
        if ((jwtData.getRole() != Role.USER && jwtData.getRole() != Role.ADMIN) || jwtData.isExpired()) {
            return;
        }
        dtoConsumer.accept(dto);
    }

}
