package com.example.restfulservice.service.impl;

import com.example.restfulservice.model.Task;
import com.example.restfulservice.repository.TaskRepository;
import com.example.restfulservice.service.CompilationService;
import com.example.restfulservice.service.TaskService;
import com.example.restfulservice.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskServiceImpl implements TaskService {

//    @Autowired
//    private ModelMapper mapper;

    @Autowired
    private CompilationService compilationService;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " can't be found"));
    }

    @Override
    public Task create(TaskDto dto) {
        Task task = new Task();
        return saveDtoToTask(dto, task);
    }

    @Override
    public Task update(TaskDto dto, Long id) {
        Task task = findById(id);
        return saveDtoToTask(dto, task);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public Task saveDtoToTask(TaskDto dto, Task task) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setCompilation(compilationService.findById(dto.getCompilation_id()));
        taskRepository.save(task);
        return task;
    }
}
