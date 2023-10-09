package com.example.taskservice.controller;

import com.example.taskservice.dto.UserCompilationDto;
import com.example.taskservice.service.UserCompilationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update_compilation")
public class UserCompilationController {

    private final UserCompilationService userCompilationService;

    public UserCompilationController(UserCompilationService userCompilationService) {
        this.userCompilationService = userCompilationService;
    }


    @PutMapping("")
    public void changeCompilationName(@RequestBody UserCompilationDto userCompilationDto) {
        userCompilationService.update(userCompilationDto);
    }
}
