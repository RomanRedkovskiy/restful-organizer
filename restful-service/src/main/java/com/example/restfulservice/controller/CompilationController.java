package com.example.restfulservice.controller;

import com.example.restfulservice.dto.CompilationDto;
import com.example.restfulservice.model.Compilation;
import com.example.restfulservice.model.Task;
import com.example.restfulservice.service.CompilationService;
import com.example.restfulservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compilations")
public class CompilationController {

    @Autowired
    private CompilationService compilationService;

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public Iterable<Compilation> getCompilation() {
        return compilationService.findAll();
    }

    @GetMapping("/{id}")
    public Compilation getCompilationById(@PathVariable final Long id) {
        return compilationService.findById(id);
    }

    @GetMapping("/{id}/tasks")
    public Iterable<Task> getAllTasksByCurrentId(@PathVariable final Long id) {
        return taskService.findTasksByCompilationId(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Compilation addCompilation(@RequestBody CompilationDto compilationDto) {
        return compilationService.create(compilationDto);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Compilation changeCompilationName(@RequestBody CompilationDto compilationDto,
                                             @PathVariable final Long id) {
        return compilationService.update(compilationDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompilation(@PathVariable final Long id) {
        compilationService.delete(id);
    }

}
