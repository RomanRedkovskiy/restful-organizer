package com.example.taskservice.service;

import com.example.taskservice.util.jwt.JwtData;
import com.example.taskservice.util.jwt.Role;

import java.util.Optional;

public interface JwtService {

    String generateAuthorizationHeader(Role role);

    Optional<JwtData> parseHeader(String header);

}
