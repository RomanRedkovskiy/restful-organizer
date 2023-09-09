package com.example.restfulservice.service;

import com.example.restfulservice.dto.CompilationDto;
import com.example.restfulservice.model.Compilation;

public interface CompilationService {

    Iterable<Compilation> findAllCompilations();

    Iterable<CompilationDto> findAllDtos();

    Compilation findCompilationById(Long id);

    CompilationDto findDtoById(Long id);

    Iterable<CompilationDto> findByUserIdAndNotShared(Long id);

    Iterable<CompilationDto> findByUserIdAndSharedAndChangeable(Long id);

    Iterable<CompilationDto> findByUserIdAndSharedAndNotChangeable(Long id);

    CompilationDto create(CompilationDto dto);

    void delete(Long id);

    void save(Compilation compilation);

}
