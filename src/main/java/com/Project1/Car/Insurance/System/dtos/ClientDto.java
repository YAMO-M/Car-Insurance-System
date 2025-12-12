package com.Project1.Car.Insurance.System.dtos;
import lombok.Builder;
@Builder
public record ClientDto(
        String email,
        boolean profileCompleted,
        String fName,
        String lName,
        String address,
        String nationalId,
        String phoneNumber
) {
}
