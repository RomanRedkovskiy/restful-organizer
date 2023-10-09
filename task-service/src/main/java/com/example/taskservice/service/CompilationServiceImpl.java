package com.example.taskservice.service;

import com.example.taskservice.auxillary.UserCompilationId;
import com.example.taskservice.dto.CompilationDto;
import com.example.taskservice.model.Compilation;
import com.example.taskservice.model.User;
import com.example.taskservice.model.UserCompilation;
import com.example.taskservice.repository.CompilationRepository;
import com.example.taskservice.repository.UserCompilationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    private final UserService userService;

    private final UserCompilationRepository userCompilationRepository;

    public CompilationServiceImpl(CompilationRepository compilationRepository, UserService userService, UserCompilationRepository userCompilationRepository) {
        this.compilationRepository = compilationRepository;
        this.userService = userService;
        this.userCompilationRepository = userCompilationRepository;
    }

    @Override
    public Iterable<Compilation> findAllCompilations() {
        return compilationRepository.findAllByIsDeleted(false);
    }

    @Override
    public Iterable<CompilationDto> findAllDtos() {
        return compilationListToDtoList(findAllCompilations());
    }

    @Override
    public Compilation findCompilationById(Long id) {
        return compilationRepository.findCompilationByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Compilation with id " + id + " can't be found"));
    }

    @Override
    public CompilationDto findDtoById(Long id) {
        return compilationToDto(findCompilationById(id));
    }

    @Override
    public Iterable<CompilationDto> findByUserIdAndNotShared(Long id) {
        return compilationListToDtoList(compilationRepository.findCompilationsByUserIdAndParams(
                id,
                false,
                false,
                false
        ));
    }

    @Override
    public Iterable<CompilationDto> findByUserIdAndSharedAndChangeable(Long id) {
        return compilationListToDtoList(compilationRepository.findCompilationsByUserIdAndParams(
                id,
                true,
                false,
                false
        ));
    }

    @Override
    public Iterable<CompilationDto> findByUserIdAndSharedAndNotChangeable(Long id) {
        return compilationListToDtoList(compilationRepository.findCompilationsByUserIdAndParams(
                id,
                true,
                true,
                false
        ));
    }

    @Override
    public CompilationDto create(CompilationDto dto) {
        Compilation compilation = new Compilation();
        return saveDtoToCompilation(dto, compilation);
    }

    @Override
    public void delete(Long id) {
        Compilation compilation = findCompilationById(id);
        compilation.setDeleted(true);
        save(compilation);
    }

    @Override
    public void save(Compilation compilation) {
        compilationRepository.save(compilation);
    }

    Iterable<CompilationDto> compilationListToDtoList(Iterable<Compilation> taskList) {
        // create a stream from the source iterable
        return StreamSupport.stream(taskList.spliterator(), false)
                .map(this::compilationToDto) // apply method to each task
                .collect(Collectors.toList()); // collect the result into a list (or any other collection)
    }

    public CompilationDto compilationToDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.getName(), compilation.getCompleteness());
    }

    public CompilationDto saveDtoToCompilation(CompilationDto dto, Compilation compilation) {
        User user = userService.findUserById(dto.getUser_id());
        compilation.setName(dto.getName());
        compilationRepository.save(compilation);
        saveUserCompilation(user, compilation, false, false);
        return compilationToDto(compilation);
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
}
