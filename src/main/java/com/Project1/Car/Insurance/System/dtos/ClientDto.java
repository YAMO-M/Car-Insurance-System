package com.Project1.Car.Insurance.System.dtos;

import com.Project1.Car.Insurance.System.entities.Policy;
import com.Project1.Car.Insurance.System.entities.Roles;
import com.Project1.Car.Insurance.System.entities.Vehicle;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ClientDto(

        UUID clientId,
        String email,
        boolean profileCompleted,
        Roles role,
        boolean isAccountActive,
        List<Policy> policies,
        List<Vehicle> vehicles,
        String fName,
        String lName,
        String address,
        String nationalId,
        String phoneNumber
) {
}
