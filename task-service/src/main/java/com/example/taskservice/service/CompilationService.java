package com.example.taskservice.service;

import com.example.taskservice.dto.CompilationDto;
import com.example.taskservice.model.Compilation;
import com.example.taskservice.model.Task;
import com.example.taskservice.model.User;
import com.example.taskservice.model.UserCompilation;
import com.example.taskservice.util.Status;
import com.example.taskservice.util.statisticMessagesEnum.CompilationChangeMessage;

import java.util.List;
import java.util.Set;

public interface CompilationService {

    Iterable<Compilation> findAllCompilations();

    Iterable<CompilationDto> findAllDtos();

    Iterable<Task> findTasksByCompilationId(Long id);

    Compilation findCompilationById(Long id);

    CompilationDto findDtoById(Long id);

    Iterable<CompilationDto> findByUserIdAndNotShared(Long id);

    Iterable<CompilationDto> findByUserIdAndSharedAndChangeable(Long id);

    Iterable<CompilationDto> findByUserIdAndSharedAndNotChangeable(Long id);

    Set<Long> findAllUserIdsThatOwnCurrentCompilation(Long id);

    Set<Long> castUsersSetToIdsSet(Set<UserCompilation> users);

    CompilationDto create(CompilationDto dto);

    void delete(Long id);

    void save(Compilation compilation);

    Iterable<CompilationDto> compilationListToDtoList(Iterable<Compilation> taskList);

    CompilationDto compilationToDto(Compilation compilation);

    CompilationDto saveDtoToCompilation(CompilationDto dto, Compilation compilation);

    void saveUserCompilation(User user, Compilation compilation, boolean readOnly, boolean isShared);

    void sendCompilationDataToMessageBroker(
            Compilation compilation,
            Set<Long> userIds,
            CompilationChangeMessage message
    );

    List<Status> getStatusListFromCompilation(List<Task> taskList);

}
