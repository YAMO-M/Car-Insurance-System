package com.Project1.Car.Insurance.System.dtos;
import lombok.Builder;
@Builder
public record ClientResponse(
        String email,
        String fName,
        String lName,
        String address,
        String nationalId,
        String phoneNumber,
        boolean profileCompleted
) {
}
