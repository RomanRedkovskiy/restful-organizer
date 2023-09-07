package com.example.restfulservice.service;

import com.example.restfulservice.dto.CompilationDto;
import com.example.restfulservice.model.Compilation;
import com.example.restfulservice.model.Task;
import com.example.restfulservice.model.User;
import com.example.restfulservice.repository.CompilationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompilationServiceImpl implements CompilationService {

    @Autowired
    private CompilationRepository compilationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Override
    public Iterable<Compilation> findAll() {
        return compilationRepository.findAllByIsDeleted(false);
    }

    @Override
    public Compilation findById(Long id) {
        return compilationRepository.findCompilationByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Compilation with id " + id + " can't be found"));
    }

    @Override
    public Iterable<Compilation> findByUserId(Long id) {
        return compilationRepository.findByUserIdAndIsDeleted(id, false);
    }

    @Override
    public Compilation create(CompilationDto dto) {
        Compilation compilation = new Compilation();
        return saveDtoToCompilation(dto, compilation);
    }

    @Override
    public Compilation update(CompilationDto dto, Long id) {
        Compilation compilation = findById(id);
        return saveDtoToCompilation(dto, compilation);
    }

    @Override
    public void delete(Long id) {
        Compilation compilation = findById(id);
        compilation.setDeleted(true);
        compilationRepository.save(compilation);
    }


    public Compilation saveDtoToCompilation(CompilationDto dto, Compilation compilation) {
        User user = userService.findById(dto.getUser_id());
        compilation.setName(dto.getName());
        compilation.getUsers().add(user);
        user.getCompilations().add(compilation);
        compilationRepository.save(compilation);
        return compilation;
    }
}
