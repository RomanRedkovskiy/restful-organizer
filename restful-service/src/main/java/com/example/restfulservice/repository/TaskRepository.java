package com.example.restfulservice.repository;

import com.example.restfulservice.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByIsDeleted(boolean is_deleted);

    List<Task> findAllByCompilationIdAndIsDeleted(Long compilation_id, boolean is_deleted);

    Optional<Task> findTaskByIdAndIsDeleted(Long id, boolean is_deleted);
}
