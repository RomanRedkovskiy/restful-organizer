package com.example.statisticservice.service;

public interface SecurityService {

    boolean hasRole(String header);

    boolean hasAdmin(String header);

}
