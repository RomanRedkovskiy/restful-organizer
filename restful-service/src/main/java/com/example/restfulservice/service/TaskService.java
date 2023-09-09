package com.example.restfulservice.service;

import com.example.restfulservice.dto.TaskDto;
import com.example.restfulservice.model.Task;

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
