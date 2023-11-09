package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.JwtService;
import com.example.userservice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@RequestHeader("Authorization") String header, @PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(HttpServletResponse response, @RequestBody User user) {
        response.setHeader("Authorization", jwtService.generateUserRegistrationHeader(user));
        return userService.create(user);
    }

    @PostMapping("/login")
    public User checkLoginCorrectness(HttpServletResponse response, @RequestBody User user) {
        user = userService.checkUserDataCorrectness(user);
        response.setHeader("Authorization", jwtService.generateUserLoginHeader(user));
        return user;
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestHeader("Authorization") String header, @RequestBody User user) {
        return userService.update(user);
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestHeader("Authorization") String header, @PathVariable final Long id) {
        userService.deleteUserById(id);
    }

}
