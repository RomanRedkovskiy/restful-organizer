package com.example.userservice.util.jwt;

import com.example.userservice.model.User;
import io.jsonwebtoken.Claims;
import utils.JwtUtils;

import java.util.Optional;

import static utils.JwtUtils.isTokenExpired;
import static utils.JwtUtils.parseToken;

public class JwtHandler {
    private final static String prefix = "Bearer ";

    public static String generateUserLoginHeader(User user){
        if (user.getId() == -1) {
            return "";
        }
        if(user.isAdmin()){
            return generateAuthorizationHeader(Role.ADMIN);
        } else {
            return generateAuthorizationHeader(Role.USER);
        }
    }

    public static String generateAuthorizationHeader(Role role) {
        return prefix + JwtUtils.generateToken(role.toString, 10 * 60 * 60 * 1000);
    }

    public static Optional<JwtData> parseHeader(String header){
        if(!header.startsWith(prefix)) {
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
        } catch(Exception e) {
            return Optional.empty();
        }

        return Optional.of(jwtData);
    }

}
