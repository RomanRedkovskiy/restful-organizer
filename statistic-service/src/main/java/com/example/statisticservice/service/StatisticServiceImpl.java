package com.example.statisticservice.service;

import com.example.statisticservice.model.Statistic;
import com.example.statisticservice.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public Statistic createStatistic(Long id) {
        return null;
    }

/*    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumeChangeTaskFromQueue(ChangeTaskDescription changeTaskDescription) {

    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumeChangeCompilationFromQueue(ChangeCompilationDescription changeCompilationDescription) {

    }*/
}
