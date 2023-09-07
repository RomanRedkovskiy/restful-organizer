package com.example.restfulservice.repository;

import com.example.restfulservice.model.Compilation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompilationRepository extends CrudRepository<Compilation, Long> {
    List<Compilation> findAllByIsDeleted(boolean is_deleted);

    @Query(value = "SELECT c.* FROM compilation c JOIN user_compilation uc ON c.id = uc.compilation_id " +
            "WHERE uc.user_id = :user_id AND c.is_deleted = :is_deleted", nativeQuery = true)
    List<Compilation> findByUserIdAndIsDeleted(@Param("user_id") Long userId, @Param("is_deleted") boolean isDeleted);

    Optional<Compilation> findCompilationByIdAndIsDeleted(Long id, boolean is_deleted);
}
