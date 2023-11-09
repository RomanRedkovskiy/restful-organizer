package com.example.taskservice.controller;

import com.example.taskservice.dto.CompilationDto;
import com.example.taskservice.dto.TaskDto;
import com.example.taskservice.service.CompilationService;
import com.example.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compilations")
public class CompilationController {

    private final CompilationService compilationService;
    private final TaskService taskService;

    @Autowired
    public CompilationController(CompilationService compilationService, TaskService taskService) {
        this.compilationService = compilationService;
        this.taskService = taskService;
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("")
    public Iterable<CompilationDto> getCompilation(@RequestHeader("Authorization") String header) {
        return compilationService.findAllDtos();
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    public CompilationDto getCompilationById(@RequestHeader("Authorization") String header,
                                             @PathVariable final Long id) {
        return compilationService.findDtoById(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}/tasks")
    public Iterable<TaskDto> getAllTasksByCurrentId(@RequestHeader("Authorization") String header,
                                                    @PathVariable final Long id) {
        return taskService.findTaskDtosByCompilationId(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestHeader("Authorization") String header,
                                         @RequestBody CompilationDto compilationDto) {
        return compilationService.create(compilationDto);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompilation(@RequestHeader("Authorization") String header,
                                  @PathVariable final Long id) {
        compilationService.delete(id);
    }

}
