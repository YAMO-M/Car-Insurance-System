package com.Project1.Car.Insurance.System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    // PART 1: REGISTER
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private boolean profileCompleted = false;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)

    private Roles role;

    @Column(nullable = false)
    @Builder.Default
    private boolean isEnabled = true;

    //RELATIONSHIPS
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Policy> policies = new ArrayList<>();

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Vehicle> vehicles = new ArrayList<>();

    // PART 2: COMPLETE PROFILE
    private String fName;

    private String lName;

    private String address;

    private LocalDate dateOfBirth;

    @Column(unique = true, updatable = false)
    private String nationalId;

    @Column(unique = true)
    private String phoneNumber;









}
