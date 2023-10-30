package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findUserByIsDeleted(boolean isDeleted);

    List<User> findAllUsersByIdNotAndIsDeleted(Long id, boolean isDeleted);

    Optional<User> findUserByNameAndIsDeleted(String name, boolean isDeleted);

    Optional<User> findUserByIdAndIsDeleted(Long id, boolean isDeleted);

    Optional<User> findUserByNameAndLoginAndPasswordAndIsDeleted(String name, String login,
                                                                 String password, boolean isDeleted);

}
