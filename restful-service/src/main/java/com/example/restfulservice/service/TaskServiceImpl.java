package com.example.restfulservice.service;

import com.example.restfulservice.model.Compilation;
import com.example.restfulservice.model.Task;
import com.example.restfulservice.repository.CompilationRepository;
import com.example.restfulservice.repository.TaskRepository;
import com.example.restfulservice.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private CompilationRepository compilationRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Iterable<TaskDto> findAll() {
        Iterable<Task> taskList = taskRepository.findAllByIsDeleted(false);
        return taskListToDtoList(taskList);
    }

    @Override
    public Task findTaskById(Long id) {
        return taskRepository.findTaskByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " can't be found"));
    }

    @Override
    public TaskDto findDtoById(Long id) {
        Task task = findTaskById(id);
        return taskToDto(task);
    }

    @Override
    public Iterable<Task> findTasksByCompilationId(Long id) {
        return taskRepository.findAllByCompilationIdAndIsDeleted(id, false);
    }

    @Override
    public Iterable<TaskDto> findTaskDtosByCompilationId(Long id) {
        return taskListToDtoList(findTasksByCompilationId(id));
    }

    @Override
    public TaskDto create(TaskDto dto) {
        Task task = new Task();
        return saveDtoToTask(dto, task);
    }

    @Override
    public TaskDto update(TaskDto dto) {
        Task task = findTaskById(dto.getId());
        return saveDtoToTask(dto, task);
    }

    @Override
    public void delete(Long id) {
        Task task = findTaskById(id);
        task.setDeleted(true);
        taskRepository.save(task);
        processCompilationChange(task.getCompilation().getId());
    }

    public TaskDto saveDtoToTask(TaskDto dto, Task task) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setCompilation(compilationRepository.findById(dto.getCompilation_id()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Compilation with id " + dto.getCompilation_id()
                        + " can't be found")));
        taskRepository.save(task);
        processCompilationChange(task.getCompilation().getId());
        return taskToDto(task);
    }

    Iterable<TaskDto> taskListToDtoList(Iterable<Task> taskList) {
        // create a stream from the source iterable
        return StreamSupport.stream(taskList.spliterator(), false)
                .map(this::taskToDto) // apply method to each task
                .collect(Collectors.toList()); // collect the result into a list (or any other collection)
    }

    public TaskDto taskToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCompilation().getId()
        );
    }

    void processCompilationChange(Long id) {
        Compilation compilation = compilationRepository.findCompilationByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Compilation with id " + id + " can't be found"));
        compilation.setCompleteness(calculateCompleteness(compilation.getId()));
        compilationRepository.save(compilation);
    }

    byte calculateCompleteness(Long id) {
        Iterable<Task> tasks = findTasksByCompilationId(id);
        int taskCount = 0;
        int completedCount = 0;
        int inProgressCount = 0;
        for (Task task : tasks) {
            taskCount++;
            if (task.getStatus().equals("In Progress")) {
                inProgressCount++;
            } else if (task.getStatus().equals("Completed")) {
                completedCount++;
            }
        }
        if (taskCount == 0) {
            return 0;
        }
        float taskValue = 100f / taskCount;
        float completeness = taskValue * (completedCount + inProgressCount / 2f);
        return (byte) completeness;
    }
}
