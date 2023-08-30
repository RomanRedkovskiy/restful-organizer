package com.example.restfulservice.controller;

import com.example.restfulservice.model.Task;
import com.example.restfulservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class HelloController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/tasks")
    public ResponseEntity<?> addTask(@RequestBody final Task task) {
        taskRepository.save(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(task.getId());
    }

    @GetMapping("/tasks")
    public Iterable<Task> getTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTask(@PathVariable final Long id) {
        Task foundedTaskById = taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " can't be found"));
        return new ResponseEntity<>(foundedTaskById, HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> replaceTask(@RequestBody final Task task, @PathVariable final Long id) {
        Task foundedTaskById = taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " can't be found"));
        foundedTaskById.setDescription(task.getDescription());
        foundedTaskById.setStatus(task.getStatus());
        taskRepository.save(foundedTaskById);
        return new ResponseEntity<>(foundedTaskById, HttpStatus.OK);
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable final Long id) {
        Task foundedTaskById = taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " can't be found"));
        taskRepository.delete(foundedTaskById);
        return new ResponseEntity<>(foundedTaskById, HttpStatus.OK);
    }
}
