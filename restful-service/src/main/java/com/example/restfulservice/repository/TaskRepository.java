package com.example.restfulservice.repository;

import com.example.restfulservice.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
