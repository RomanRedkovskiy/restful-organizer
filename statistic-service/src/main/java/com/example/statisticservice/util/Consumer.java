package com.example.statisticservice.util;

public interface Consumer<T> {
    void accept(T dto);
}
