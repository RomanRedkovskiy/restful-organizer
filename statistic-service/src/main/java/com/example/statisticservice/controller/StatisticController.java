package com.example.statisticservice.controller;

import com.example.statisticservice.dto.StatisticDto;
import com.example.statisticservice.model.Statistic;
import com.example.statisticservice.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @PreAuthorize("@securityService.hasAdmin(#header)")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<StatisticDto> getStatistics(@RequestHeader("Authorization") String header) {
        return statisticService.getStatisticDtos();
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StatisticDto getStatistic(@RequestHeader("Authorization") String header, @PathVariable final Long id) {
        return statisticService.getStatisticDto(id);
    }
}
