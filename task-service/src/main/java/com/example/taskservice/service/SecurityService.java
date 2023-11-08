package com.example.taskservice.service;

import com.example.taskservice.util.jwt.JwtData;
import com.example.taskservice.util.jwt.JwtHandler;
import com.example.taskservice.util.jwt.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("securityService")
public class SecurityService {

    @Autowired
    UserService userService;

    public boolean hasRole(String header) {
        Set<Role> roles = new HashSet<>(Set.of(Role.ADMIN, Role.USER));
        return roleFounder(header, roles);
    }

    public boolean hasAdmin(String header){
        Set<Role> roles = Collections.singleton(Role.ADMIN);
        return roleFounder(header, roles);
    }

    public boolean roleFounder(String header, Set<Role> roles){
        Optional<JwtData> jwtOptional = JwtHandler.parseHeader(header);
        if (jwtOptional.isEmpty()) {
            return false;
        }
        JwtData jwtData = jwtOptional.get();
        for(Role role: roles){
            if(jwtData.getRole() == role){
                return true;
            }
        }
        return false;
    }
}
