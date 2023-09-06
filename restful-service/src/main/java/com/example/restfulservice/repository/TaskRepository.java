package com.example.restfulservice.repository;

import com.example.restfulservice.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByCompilationId(Long compilation_id);
}
