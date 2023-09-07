package com.example.restfulservice.controller;

import com.example.restfulservice.dto.UserDto;
import com.example.restfulservice.model.Compilation;
import com.example.restfulservice.model.Task;
import com.example.restfulservice.model.User;
import com.example.restfulservice.repository.CompilationRepository;
import com.example.restfulservice.repository.UserRepository;
import com.example.restfulservice.service.CompilationService;
import com.example.restfulservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompilationService compilationService;

    @GetMapping("")
    public Iterable<User> getCompilation() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable final Long id) {
        return userService.findById(id);
    }

    @GetMapping("/except/{id}")
    public Iterable<User> getUsersExceptId(@PathVariable final Long id){
        return userService.findAllExceptId(id);
    }

    @GetMapping("/{id}/compilations")
    public Iterable<Compilation> getAllTasksByCurrentId(@PathVariable final Long id) {
        return compilationService.findByUserId(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        return userService.update(userDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable final Long id){
        userService.delete(id);
    }

}
