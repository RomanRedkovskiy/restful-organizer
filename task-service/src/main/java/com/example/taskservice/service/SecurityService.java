package com.example.taskservice.service;

import com.example.taskservice.util.jwt.JwtData;
import com.example.taskservice.util.jwt.JwtHandler;
import com.example.taskservice.util.jwt.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("securityService")
public class SecurityService {

    @Autowired
    UserService userService;

    public boolean hasRole(String header) {
        Optional<JwtData> jwtOptional = JwtHandler.parseHeader(header);
        if (jwtOptional.isEmpty()) {
            return false;
        }
        JwtData jwtData = jwtOptional.get();
        return (jwtData.getRole() == Role.USER || jwtData.getRole() == Role.ADMIN) && !jwtData.isExpired();
    }
}
