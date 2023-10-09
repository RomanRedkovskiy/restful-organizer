package com.example.taskservice.controller;

import com.example.taskservice.dto.CompilationDto;
import com.example.taskservice.dto.TaskDto;
import com.example.taskservice.service.CompilationService;
import com.example.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compilations")
public class CompilationController {

    @Autowired
    private CompilationService compilationService;

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public Iterable<CompilationDto> getCompilation() {
        return compilationService.findAllDtos();
    }

    @GetMapping("/{id}")
    public CompilationDto getCompilationById(@PathVariable final Long id) {
        return compilationService.findDtoById(id);
    }

    @GetMapping("/{id}/tasks")
    public Iterable<TaskDto> getAllTasksByCurrentId(@PathVariable final Long id) {
        return taskService.findTaskDtosByCompilationId(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestBody CompilationDto compilationDto) {
        return compilationService.create(compilationDto);
    }

/*
    @PutMapping("/share")
    @ResponseStatus(HttpStatus.OK)
    public void shareCompilation(@RequestBody UserCompilation userCompilation) {
        compilationService.share(userCompilation);
    }
*/

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompilation(@PathVariable final Long id) {
        compilationService.delete(id);
    }

}
