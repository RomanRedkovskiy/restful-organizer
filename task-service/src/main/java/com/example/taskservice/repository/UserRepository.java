package com.example.taskservice.repository;

import com.example.taskservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByIsDeleted(boolean is_deleted);

    List<User> findAllByIdNotAndIsDeleted(Long id, boolean is_deleted);

    Optional<User> findUserByIdAndIsDeleted(Long id, boolean is_deleted);

    Optional<User> findUserByNameAndIsDeleted(String name, boolean is_deleted);

    Optional<User> findUserByNameAndLoginAndPasswordAndIsDeleted(String name,
                                                                 String login,
                                                                 String password,
                                                                 boolean is_deleted);
}
