package com.example.taskservice.service;

import com.example.taskservice.dto.TaskDto;

public interface TaskService {

    Iterable<TaskDto> findAll();

    TaskDto findDtoById(Long id);

    Iterable<TaskDto> findTaskDtosByCompilationId(Long id);

    TaskDto create(TaskDto dto);

    TaskDto update(TaskDto dto);

    void delete(Long id);
}
