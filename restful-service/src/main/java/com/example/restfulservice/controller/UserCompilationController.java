package com.example.restfulservice.controller;

import com.example.restfulservice.dto.UserCompilationDto;
import com.example.restfulservice.repository.UserCompilationRepository;
import com.example.restfulservice.service.UserCompilationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update_compilation")
public class UserCompilationController {

    @Autowired
    UserCompilationService userCompilationService;

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void changeCompilationName(@RequestBody UserCompilationDto userCompilationDto) {
        userCompilationService.update(userCompilationDto);
    }
}
