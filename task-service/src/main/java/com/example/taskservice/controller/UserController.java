package com.example.taskservice.controller;

import com.example.taskservice.dto.CompilationDto;
import com.example.taskservice.dto.UserDto;
import com.example.taskservice.service.CompilationService;
import com.example.taskservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompilationService compilationService;

    @GetMapping("")
    public Iterable<UserDto> getCompilation() {
        return userService.findAllDtos();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable final Long id) {
        return userService.findDtoById(id);
    }

    @GetMapping("/except/{id}")
    public Iterable<UserDto> getUsersExceptId(@PathVariable final Long id){
        return userService.findAllDtosExceptId(id);
    }

    @GetMapping("/{id}/self_compilations")
    public Iterable<CompilationDto> getAllSelfCompilationsByCurrentId(@PathVariable final Long id) {
        return compilationService.findByUserIdAndNotShared(id);
    }

    @GetMapping("/{id}/shared_compilations")
    public Iterable<CompilationDto> getAllSharedCompilationsByCurrentId(@PathVariable final Long id) {
        return compilationService.findByUserIdAndSharedAndChangeable(id);
    }

    @GetMapping("/{id}/readonly_compilations")
    public Iterable<CompilationDto> getAllUnchangeableCompilationsByCurrentId(@PathVariable final Long id) {
        return compilationService.findByUserIdAndSharedAndNotChangeable(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PostMapping("/login")
    public UserDto checkLoginCorrectness(@RequestBody UserDto userDto){
        return userService.checkUserDataCorrectness(userDto);
    }

    @PutMapping("")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable final Long id){
        userService.delete(id);
    }

}
