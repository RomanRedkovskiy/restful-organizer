package com.example.restfulservice.controller;

import com.example.restfulservice.model.Task;
import com.example.restfulservice.service.TaskService;
import com.example.restfulservice.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public Iterable<Task> getTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTask(@PathVariable final Long id) {
        return taskService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(@RequestBody final TaskDto taskDto, @PathVariable final Long id) {
        return taskService.update(taskDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable final Long id) {
        taskService.delete(id);
    }
}
