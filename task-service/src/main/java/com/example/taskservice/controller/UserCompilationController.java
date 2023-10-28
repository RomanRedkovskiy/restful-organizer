package com.example.taskservice.controller;

import com.example.taskservice.dto.UserCompilationDto;
import com.example.taskservice.service.UserCompilationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update_compilation")
public class UserCompilationController {

    private final UserCompilationService userCompilationService;

    public UserCompilationController(UserCompilationService userCompilationService) {
        this.userCompilationService = userCompilationService;
    }

    @PreAuthorize("@securityService.hasRole(#header)")
    @PutMapping("")
    public void changeCompilationName(@RequestHeader("Authorization") String header,
                                      @RequestBody UserCompilationDto userCompilationDto) {
        userCompilationService.update(userCompilationDto);
    }
}
