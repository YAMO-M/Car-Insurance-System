package com.Project1.Car.Insurance.System.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VehicleRequest(
        @NotNull
        @Size(min = 3,message = "minimum of 3 characters required")
        String make,
        @NotNull
        @Size(min = 3,message = "minimum of 3 characters required")
        String model,
        @NotNull
        @Pattern(regexp = "^[A-HJ-NPR-Z0-9]{17}$",message = "Invalid vehicle VIN")
        String vin,
        @NotNull
        Integer year,
        @NotNull
        @Pattern(regexp = "[A-Za-z\\d]{4,16}$" ,message = "licence plate must be 4-16 char, only letters and numbers")
        String licence_plate
) {
}
