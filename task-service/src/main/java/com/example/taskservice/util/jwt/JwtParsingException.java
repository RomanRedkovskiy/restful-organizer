package com.example.taskservice.util.jwt;

public class JwtParsingException extends RuntimeException{

    public JwtParsingException(String message) {
        super(message);
    }
}
