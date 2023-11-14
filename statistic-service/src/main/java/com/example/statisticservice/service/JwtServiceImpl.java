package com.example.statisticservice.service;

import com.example.statisticservice.util.jwt.JwtData;
import com.example.statisticservice.util.jwt.JwtParsingException;
import com.example.statisticservice.util.jwt.Role;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import utils.JwtUtils;

import java.util.Optional;

import static utils.JwtUtils.isTokenExpired;
import static utils.JwtUtils.parseToken;

@Service
public class JwtServiceImpl implements JwtService{

    private final static String prefix = "Bearer ";

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
        } catch (JwtParsingException e) {
            return Optional.empty();
        }

        return Optional.of(jwtData);
    }

}
