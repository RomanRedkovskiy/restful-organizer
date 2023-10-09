package com.example.statisticservice.model;

import jakarta.persistence.*;


@Entity
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int compilationNumber;

    public Statistic() {
    }

}
