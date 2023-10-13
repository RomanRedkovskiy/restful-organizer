package com.example.statisticservice.service;

import com.example.statisticservice.dto.UserChangeDto;
import com.example.statisticservice.model.Statistic;
import com.example.statisticservice.repository.StatisticRepository;
import com.example.statisticservice.config.RabbitMQConfig;
import com.example.statisticservice.util.statisticMessagesEnum.UserChangeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public void createStatisticById(Long id) {
        Statistic userStat = new Statistic(id);
        statisticRepository.save(userStat);
    }

    @Override
    public void saveStatistic(Statistic statistic) {
        statisticRepository.save(statistic);
    }

    @Override
    public void deleteStatisticById(Long id) {
        Statistic statistic = findStatisticById(id);
        statistic.setDeleted(true);
        saveStatistic(statistic);
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void consumeChangeUserFromQueue(UserChangeDto userChange) {
        if(userChange.getMessage().equals(UserChangeMessage.REGISTER)){
            createStatisticById(userChange.getUserId());
        } else if(userChange.getMessage().equals(UserChangeMessage.DELETE)){
            deleteStatisticById(userChange.getUserId());
        }
    }

}
