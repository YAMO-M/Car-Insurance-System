package com.Project1.Car.Insurance.System.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(unique = true,updatable = false)
    private String idNumber;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Roles role = Roles.CLIENT;

    boolean profileCompleted = false;





}
