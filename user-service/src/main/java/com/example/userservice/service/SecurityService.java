package com.example.userservice.service;

import com.example.userservice.util.jwt.JwtData;
import com.example.userservice.util.jwt.JwtHandler;
import com.example.userservice.util.jwt.Role;
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
