package com.example.taskservice.repository;

import com.example.taskservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByIsDeleted(boolean isDeleted);

    List<User> findAllByIdNotAndIsDeleted(Long id, boolean isDeleted);

    Optional<User> findUserByIdAndIsDeleted(Long id, boolean isDeleted);

    Optional<User> findUserByNameAndIsDeleted(String name, boolean isDeleted);

}
