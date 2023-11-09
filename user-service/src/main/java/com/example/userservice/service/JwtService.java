package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.util.jwt.JwtData;
import com.example.userservice.util.jwt.Role;

import java.util.Optional;

public interface JwtService {

    String generateUserRegistrationHeader(User user);

    String generateUserLoginHeader(User user);

    String generateAuthorizationHeader(Role role);

    Optional<JwtData> parseHeader(String header);

}
