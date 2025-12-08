package com.Project1.Car.Insurance.System.entities;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;

    private String fName;

    private String lName;

    private String address;

    @Column(unique = true)
    private String idNumber;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Roles role;

    @Column(nullable = false)
    private boolean profileCompleted = false;

    @Column(nullable = false)
    private boolean isAccountActive;

    @Column(name = "policies")
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Policy> policies = new ArrayList<>();

    @Column(name = "vehicles")
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Vehicle> vehicles = new ArrayList<>();







}
