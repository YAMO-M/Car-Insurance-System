package com.Project1.Car.Insurance.System.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UpdateVehicleRequest(
        @NotNull
        @org.hibernate.validator.constraints.UUID
        UUID carId,

        @NotNull
        @Pattern(regexp = "[A-Za-z\\d]{4,16}$" ,message = "password must be 4-16 char, only letters and numbers")
        String licence_plate

) {
}
