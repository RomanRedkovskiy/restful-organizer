package com.example.statisticservice.service;

import com.example.statisticservice.dto.UserChangeDto;
import com.example.statisticservice.model.Statistic;

public interface StatisticService {

    Statistic findStatisticById(Long id);

    void createStatisticById(Long id);

    void saveStatistic(Statistic statistic);

    void deleteStatisticById(Long id);


    //methods to consume data from RabbitMQ
    void consumeChangeUserFromQueue(UserChangeDto user);


}
