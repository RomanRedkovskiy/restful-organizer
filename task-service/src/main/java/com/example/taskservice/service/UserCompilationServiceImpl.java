package com.example.taskservice.service;

import com.example.taskservice.auxillary.UserCompilationId;
import com.example.taskservice.dto.UserCompilationDto;
import com.example.taskservice.model.Compilation;
import com.example.taskservice.model.User;
import com.example.taskservice.model.UserCompilation;
import com.example.taskservice.repository.UserCompilationRepository;
import com.example.taskservice.util.statisticMessagesEnum.CompilationChangeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserCompilationServiceImpl implements UserCompilationService {

    @Autowired
    CompilationService compilationService;

    @Autowired
    UserService userService;

    @Autowired
    UserCompilationRepository userCompilationRepository;

    @Override
    public void update(UserCompilationDto dto) {
        Compilation compilation = compilationService.findCompilationById(dto.getCompilation_id());
        if (userOwnsCompilation(compilation.getId(), dto.getUser_id())) {
            compilation.setName(dto.getName());
            compilationService.save(compilation);
        } else {
            compilationService.sendCompilationDataToMessageBroker(compilation, Collections.singleton(dto.getUser_id()),
                    CompilationChangeMessage.ADD);
            saveUserCompilation(userService.findUserById(dto.getUser_id()), compilation,
                    dto.isRead_only(), dto.isIs_shared());
        }
    }

    public void saveUserCompilation(User user, Compilation compilation, boolean readOnly, boolean isShared) {
        UserCompilation userCompilation = new UserCompilation(
                new UserCompilationId(user.getId(), compilation.getId()),
                readOnly,
                isShared,
                user,
                compilation
        );
        userCompilationRepository.save(userCompilation);
    }

    public boolean userOwnsCompilation(Long compilationId, Long userId) {
        return compilationService.findAllUserIdsThatOwnCurrentCompilation(compilationId).contains(userId);
    }
}
