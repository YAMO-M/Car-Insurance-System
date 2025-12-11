package com.Project1.Car.Insurance.System.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID vehicleId;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String vin;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String licence_plate;

    // RELATIONSHIPS
    @OneToOne(mappedBy = "vehicle")
    private Policy policy;

    @ManyToOne
    private Client client;



}
