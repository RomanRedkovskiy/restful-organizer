package com.example.restfulservice.service;

import com.example.restfulservice.model.Task;
import com.example.restfulservice.dto.TaskDto;

public interface TaskService {

    Iterable<Task> findAll();

    Task findById(Long id);

    Iterable<Task> findTasksByCompilationId(Long id);

    Task create(TaskDto dto);

    Task update(TaskDto dto, Long id);

    void delete(Long id);
}
