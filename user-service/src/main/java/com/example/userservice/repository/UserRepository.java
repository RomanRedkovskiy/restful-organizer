package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByIdAndIsDeleted(Long id, boolean isDeleted);
}
