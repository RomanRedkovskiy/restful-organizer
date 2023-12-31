package com.example.taskservice.repository;

import com.example.taskservice.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByIsDeleted(boolean isDeleted);

    List<Task> findAllByCompilationIdAndIsDeleted(Long compilationId, boolean isDeleted);

    Optional<Task> findTaskByIdAndIsDeleted(Long id, boolean isDeleted);
}
