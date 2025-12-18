package com.Project1.Car.Insurance.System.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @Size(min = 5,message = "minimum of 5 characters required")
        @NotNull
        String address,
        @Pattern(regexp = "^(\\+27|0)[6-8][0-9]{8}$", message = "Invalid SA phone number")
        @NotNull
        String phoneNumber
) {
}
