package com.example.taskservice.controller;

import com.example.taskservice.dto.CompilationDto;
import com.example.taskservice.dto.UserDto;
import com.example.taskservice.service.CompilationService;
import com.example.taskservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CompilationService compilationService;
    private final UserService userService;

    @Autowired
    public UserController(CompilationService compilationService, UserService userService) {
        this.compilationService = compilationService;
        this.userService = userService;
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("")
    public Iterable<UserDto> getUsers(@RequestHeader("Authorization") String header) {
        return userService.findAllUserDtos();
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    public UserDto getUserById(@RequestHeader("Authorization") String header, @PathVariable final Long id) {
        return userService.findUserDtoById(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/except/{id}")
    public Iterable<UserDto> getUsersExceptId(@RequestHeader("Authorization") String header,
                                              @PathVariable final Long id) {
        return userService.findAllDtosExceptId(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}/self_compilations")
    public Iterable<CompilationDto> getAllSelfCompilationsByCurrentId(@RequestHeader("Authorization") String header,
                                                                      @PathVariable final Long id) {
        return compilationService.findByUserIdAndNotShared(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}/shared_compilations")
    public Iterable<CompilationDto> getAllSharedCompilationsByCurrentId(@RequestHeader("Authorization") String header,
                                                                        @PathVariable final Long id) {
        return compilationService.findByUserIdAndSharedAndChangeable(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}/readonly_compilations")
    public Iterable<CompilationDto> getAllUnchangeableCompilationsByCurrentId(
            @RequestHeader("Authorization") String header,
            @PathVariable final Long id) {
        return compilationService.findByUserIdAndSharedAndNotChangeable(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}/is_admin")
    public boolean isUserAdmin(
            @RequestHeader("Authorization") String header,
            @PathVariable final Long id) {
        return userService.isAdmin(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}/name")
    public String getUserName(
            @RequestHeader("Authorization") String header,
            @PathVariable final Long id) {
        return userService.getUserNameById(id);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @PutMapping("")
    public UserDto updateUser(@RequestHeader("Authorization") String header, @RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestHeader("Authorization") String header, @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestHeader("Authorization") String header, @PathVariable final Long id) {
        userService.delete(id);
    }

}
