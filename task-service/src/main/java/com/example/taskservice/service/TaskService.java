package com.example.taskservice.service;

import com.example.taskservice.dto.TaskDto;
import com.example.taskservice.model.Task;

import java.util.Set;

public interface TaskService {

    Iterable<TaskDto> findAll();

    Task findTaskById(Long id);

    TaskDto findDtoById(Long id);

    Iterable<Task> findTasksByCompilationId(Long id);

    Iterable<TaskDto> findTaskDtosByCompilationId(Long id);

    TaskDto create(TaskDto dto);

    TaskDto update(TaskDto dto);

    void delete(Long id);
}
