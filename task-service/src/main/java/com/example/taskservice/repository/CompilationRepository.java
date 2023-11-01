package com.example.taskservice.repository;

import com.example.taskservice.model.Compilation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompilationRepository extends CrudRepository<Compilation, Long> {
    List<Compilation> findAllByIsDeleted(boolean is_deleted);

    @Query(value = "SELECT c.* FROM compilation c JOIN user_compilation uc ON c.id = uc.compilation_id " +
            "WHERE uc.user_id = :userId AND uc.isShared = :isShared AND uc.readOnly = :readOnly " +
            "AND c.isDeleted = :isDeleted", nativeQuery = true)
    List<Compilation> findCompilationsByUserIdAndParams(@Param("userId") Long userId,
                                                        @Param("isShared") boolean isShared,
                                                        @Param("readOnly") boolean readOnly,
                                                        @Param("isDeleted") boolean isDeleted);

    Optional<Compilation> findCompilationByIdAndIsDeleted(Long id, boolean isDeleted);
}
