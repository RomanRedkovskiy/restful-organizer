package com.example.restfulservice.service.impl;

import com.example.restfulservice.dto.CompilationDto;
import com.example.restfulservice.model.Compilation;
import com.example.restfulservice.model.Task;
import com.example.restfulservice.repository.CompilationRepository;
import com.example.restfulservice.repository.TaskRepository;
import com.example.restfulservice.repository.UserRepository;
import com.example.restfulservice.service.CompilationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompilationServiceImpl implements CompilationService {

    @Autowired
    private CompilationRepository compilationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Iterable<Compilation> findAll() {
        return compilationRepository.findAll();
    }

    @Override
    public Compilation findById(Long id) {
        return compilationRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Compilation with id " + id + " can't be found"));
    }

    @Override
    public Iterable<Task> findTasksById(Long id) {
        return taskRepository.findByCompilationId(id);
    }

    @Override
    public Compilation create(CompilationDto dto) {
        Compilation compilation = new Compilation();
        return saveDtoToCompilation(dto, compilation);
    }

    @Override
    public Compilation update(CompilationDto dto, Long id) {
        Compilation compilation = findById(id);
        return saveDtoToCompilation(dto, compilation);
    }

    @Override
    public void delete(Long id) {
        compilationRepository.deleteById(id);
    }


    public Compilation saveDtoToCompilation(CompilationDto dto, Compilation compilation) {
        compilation.setName(dto.getName());
        //compilation.setCompleteness(calculateCompleteness(dto.getId()));
        compilation.getUsers().add(userRepository.findById(dto.getUser_id()).orElseThrow((() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with id " + dto.getUser_id() + " can't be found"))));
        compilationRepository.save(compilation);
        return compilation;
    }

    byte calculateCompleteness(Long id){
        Iterable<Task> tasks = findTasksById(id);
        float taskValue = 0;
        float completeness = 0;
        for(Task task: tasks){
            taskValue++;
        }
        if(taskValue == 0){
            return 0;
        }
        taskValue = 100 / taskValue;
        for(Task task: tasks){
            if(task.getStatus().equals("In Progress")){
                completeness += taskValue / 2;
            } else if(task.getStatus().equals("Completed")){
                completeness += taskValue;
            }
        }
        return (byte) completeness;
    }
}
