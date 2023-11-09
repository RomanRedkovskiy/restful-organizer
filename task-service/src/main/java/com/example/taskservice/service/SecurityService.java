package com.example.taskservice.service;

public interface SecurityService {

    boolean hasRole(String header);

    boolean hasAdmin(String header);

}
