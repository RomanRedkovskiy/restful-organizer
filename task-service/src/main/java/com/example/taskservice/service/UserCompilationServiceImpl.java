package com.example.taskservice.service;

import com.example.taskservice.dto.UserCompilationDto;
import com.example.taskservice.model.Compilation;
import com.example.taskservice.util.statisticMessagesEnum.CompilationChangeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserCompilationServiceImpl implements UserCompilationService {

    private final CompilationService compilationService;

    private final UserService userService;

    @Autowired
    public UserCompilationServiceImpl(CompilationService compilationService, UserService userService) {
        this.compilationService = compilationService;
        this.userService = userService;
    }

    @Override
    public UserCompilationDto update(UserCompilationDto dto) {
        Compilation compilation = compilationService.findCompilationById(dto.getCompilationId());
        if (userOwnsCompilation(compilation.getId(), dto.getUserId())) {
            compilation.setName(dto.getName());
            compilationService.save(compilation);
        } else {
            compilationService.sendCompilationDataToMessageBroker(compilation, Collections.singleton(dto.getUserId()),
                    CompilationChangeMessage.ADD);
            compilationService.saveUserCompilation(userService.findUserById(dto.getUserId()), compilation,
                    dto.isReadOnly(), dto.isShared());
        }
        return dto;
    }

    private boolean userOwnsCompilation(Long compilationId, Long userId) {
        return compilationService.findAllUserIdsThatOwnCurrentCompilation(compilationId).contains(userId);
    }
}
