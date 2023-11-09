package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.jwt.JwtData;
import com.example.userservice.util.jwt.Role;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JwtUtils;

import java.util.Optional;

import static utils.JwtUtils.isTokenExpired;
import static utils.JwtUtils.parseToken;

@Service
public class JwtServiceImpl implements JwtService{

    private final UserRepository userRepository;

    @Autowired
    public JwtServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final static String prefix = "Bearer ";

    @Override
    public String generateUserRegistrationHeader(User user) {
        if(userRepository.findUserByNameAndIsDeleted(user.getName(), false).isPresent()){
            return "";
        }
        return generateAuthorizationHeader(Role.USER);
    }

    @Override
    public String generateUserLoginHeader(User user) {
        if (user.getId() == -1) {
            return "";
        }
        if (user.isAdmin()) {
            return generateAuthorizationHeader(Role.ADMIN);
        }
        return generateAuthorizationHeader(Role.USER);
    }

    @Override
    public String generateAuthorizationHeader(Role role) {
        return prefix + JwtUtils.generateToken(role.getString(), 10 * 60 * 60 * 1000);
    }

    @Override
    public Optional<JwtData> parseHeader(String header) {
        if (!header.startsWith(prefix)) {
            return Optional.empty();
        }
        String token = header.substring(prefix.length());
        Claims tokenBody = parseToken(token);
        if (tokenBody == null) {
            return Optional.empty();
        }
        JwtData jwtData = new JwtData();
        try {
            jwtData.setExpired(isTokenExpired(tokenBody.getExpiration()));
            jwtData.setRole(Role.fromString(tokenBody.getSubject()));
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(jwtData);
    }

}
