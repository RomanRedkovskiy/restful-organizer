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
            "WHERE uc.user_id = :user_id AND uc.is_shared = :is_shared AND uc.read_only = :read_only " +
            "AND c.is_deleted = :is_deleted", nativeQuery = true)
    List<Compilation> findCompilationsByUserIdAndParams(@Param("user_id") Long userId,
                                                        @Param("is_shared") boolean isShared,
                                                        @Param("read_only") boolean readOnly,
                                                        @Param("is_deleted") boolean isDeleted);

    Optional<Compilation> findCompilationByIdAndIsDeleted(Long id, boolean is_deleted);
}
