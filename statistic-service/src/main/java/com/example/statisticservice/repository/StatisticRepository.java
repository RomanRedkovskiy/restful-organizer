package com.example.statisticservice.repository;

import com.example.statisticservice.model.Statistic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatisticRepository extends CrudRepository<Statistic, Long> {

    Optional<Statistic> findStatisticByUserIdAndIsDeleted(Long id, boolean isDeleted);

}
