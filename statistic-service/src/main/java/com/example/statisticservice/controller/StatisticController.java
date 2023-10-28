package com.example.statisticservice.controller;

import com.example.statisticservice.model.Statistic;
import com.example.statisticservice.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Statistic getStatistic(@RequestHeader("Authorization") String header, @PathVariable final Long id) {
        return statisticService.findStatisticById(id);
    }
}
