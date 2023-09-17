package com.example.restfulservice.controller;

import com.example.restfulservice.dto.UserCompilationDto;
import com.example.restfulservice.service.UserCompilationService;
import org.springframework.http.HttpStatus;
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
