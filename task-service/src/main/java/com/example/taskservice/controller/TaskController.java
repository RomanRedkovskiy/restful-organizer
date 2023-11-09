package com.example.taskservice.controller;

import com.example.taskservice.dto.TaskDto;
import com.example.taskservice.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public Iterable<TaskDto> getTasks() {
        return taskService.findAll();
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTask(@RequestHeader("Authorization") String header, @PathVariable final Long id) {
        return taskService.findDtoById(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto addTask(@RequestHeader("Authorization") String header, @RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTask(@RequestHeader("Authorization") String header, @RequestBody final TaskDto taskDto) {
        return taskService.update(taskDto);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@RequestHeader("Authorization") String header, @PathVariable final Long id) {
        taskService.delete(id);
    }
}
