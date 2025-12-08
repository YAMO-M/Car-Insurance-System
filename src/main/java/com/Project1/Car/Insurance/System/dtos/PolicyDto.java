package com.Project1.Car.Insurance.System.dtos;

import com.Project1.Car.Insurance.System.entities.PolicyType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PolicyDto(
        @NotNull
        PolicyType policyType,
        @NotNull
        UUID carId
) {
}
