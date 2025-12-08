package com.Project1.Car.Insurance.System.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID policyId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean policyStatus; //let ai decide

    @Column(nullable = false)
    private Double premiumAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;




}
