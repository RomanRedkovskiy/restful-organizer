package com.example.restfulservice.service;

import com.example.restfulservice.dto.CompilationDto;
import com.example.restfulservice.dto.TaskDto;
import com.example.restfulservice.model.Compilation;
import com.example.restfulservice.model.Task;

public interface CompilationService {

    Iterable<Compilation> findAll();

    Compilation findById(Long id);

    Iterable<Task> findTasksById(Long id);

    Compilation create(CompilationDto dto);

    Compilation update(CompilationDto dto, Long id);

    void delete(Long id);

}
