package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import com.example.userservice.util.jwt.JwtHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(HttpServletResponse response, @RequestBody User user) {
        response.setHeader("Authorization", JwtHandler.generateAuthorizationHeader());
        userService.create(user);
    }

    @PostMapping("/login")
    public void checkLoginCorrectness(HttpServletResponse response, @RequestBody User userDto){
        if(userService.checkUserDataCorrectness(userDto)){
            response.setHeader("Authorization", JwtHandler.generateAuthorizationHeader());
        }
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestHeader("Authorization") String header, @PathVariable final Long id){
        userService.deleteUserById(id);
    }

}
