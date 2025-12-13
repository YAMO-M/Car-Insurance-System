package com.Project1.Car.Insurance.System.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID adminId;

    @Column(nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Roles role = Roles.ADMIN;

    @Column(nullable = false)
    private String passkey;







}
