package com.example.restfulservice.controller;

import com.example.restfulservice.model.Compilation;
import com.example.restfulservice.model.Task;
import com.example.restfulservice.model.User;
import com.example.restfulservice.repository.CompilationRepository;
import com.example.restfulservice.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private CompilationRepository compilationRepository;

    @GetMapping("")
    public Iterable<User> getCompilation() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable final Long id){
        User foundedUserById = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " can't be found"));
        return new ResponseEntity<>(foundedUserById, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addCompilation(@RequestBody User user){
        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(user.getId());
    }

    @PostMapping("/{id}/compilations")
    public ResponseEntity<?> addCompilation(@RequestBody Compilation compilation, @PathVariable final Long id) {
        User foundedUserById = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " can't be found"));
        compilation.setCompleteness((byte) 0);
        compilation.getUsers().add(foundedUserById);
        compilationRepository.save(compilation);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(compilation.getId());
    }
}
