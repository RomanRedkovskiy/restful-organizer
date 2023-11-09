package com.example.statisticservice.service;

import com.example.statisticservice.util.jwt.JwtData;
import com.example.statisticservice.util.jwt.Role;

import java.util.Optional;

public interface JwtService {

    String generateAuthorizationHeader(Role role);

    Optional<JwtData> parseHeader(String header);

}
