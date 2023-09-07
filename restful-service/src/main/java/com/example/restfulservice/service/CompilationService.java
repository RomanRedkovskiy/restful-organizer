package com.example.restfulservice.service;

import com.example.restfulservice.dto.CompilationDto;
import com.example.restfulservice.model.Compilation;

public interface CompilationService {

    Iterable<Compilation> findAll();

    Compilation findById(Long id);

    Iterable<Compilation> findByUserId(Long id);

    Compilation create(CompilationDto dto);

    Compilation update(CompilationDto dto, Long id);

    void delete(Long id);

}
