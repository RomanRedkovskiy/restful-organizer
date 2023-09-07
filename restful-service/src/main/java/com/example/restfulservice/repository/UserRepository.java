package com.example.restfulservice.repository;

import com.example.restfulservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAllByIsDeleted(boolean is_deleted);

    List<User> findAllByIdNotAndIsDeleted(Long id, boolean is_deleted);

    Optional<User> findUserByIdAndIsDeleted(Long id, boolean is_deleted);

}
