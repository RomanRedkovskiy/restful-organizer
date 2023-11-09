package com.example.taskservice.service;

import com.example.taskservice.dto.CompilationDto;
import com.example.taskservice.model.Compilation;
import com.example.taskservice.model.User;
import com.example.taskservice.util.statisticMessagesEnum.CompilationChangeMessage;

import java.util.Set;

public interface CompilationService {

    Iterable<CompilationDto> findAllDtos();

    Compilation findCompilationById(Long id);

    CompilationDto findDtoById(Long id);

    Iterable<CompilationDto> findByUserIdAndNotShared(Long id);

    Iterable<CompilationDto> findByUserIdAndSharedAndChangeable(Long id);

    Iterable<CompilationDto> findByUserIdAndSharedAndNotChangeable(Long id);

    Set<Long> findAllUserIdsThatOwnCurrentCompilation(Long id);

    CompilationDto create(CompilationDto dto);

    void delete(Long id);

    void save(Compilation compilation);

    void sendCompilationDataToMessageBroker(Compilation compilation, Set<Long> userIds,
                                            CompilationChangeMessage message);

    void saveUserCompilation(User user, Compilation compilation, boolean readOnly, boolean isShared);
}
