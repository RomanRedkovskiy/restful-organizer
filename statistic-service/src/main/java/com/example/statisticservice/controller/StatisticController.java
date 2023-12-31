package com.example.statisticservice.controller;

import com.example.statisticservice.dto.StatisticDto;
import com.example.statisticservice.service.StatisticService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

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
