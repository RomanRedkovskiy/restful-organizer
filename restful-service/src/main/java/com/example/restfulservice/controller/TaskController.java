package com.example.restfulservice.controller;

import com.example.restfulservice.service.TaskService;
import com.example.restfulservice.dto.TaskDto;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTask(@PathVariable final Long id) {
        return taskService.findDtoById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto addTask(@RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTask(@RequestBody final TaskDto taskDto) {
        return taskService.update(taskDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable final Long id) {
        taskService.delete(id);
    }
}
