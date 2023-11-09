package com.example.userservice.service;

public interface SecurityService {

    boolean hasRole(String header);

    boolean hasAdmin(String header);

}