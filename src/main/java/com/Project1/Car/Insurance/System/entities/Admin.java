package com.Project1.Car.Insurance.System.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID adminId;

    private String fullName;

    @Enumerated(value = EnumType.STRING)
    private Roles role = Roles.ADMIN;







}
