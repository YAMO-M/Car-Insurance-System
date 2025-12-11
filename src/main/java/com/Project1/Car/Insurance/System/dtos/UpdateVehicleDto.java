package com.Project1.Car.Insurance.System.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateVehicleDto(
        @NotNull
        String licence_plate
) {
}
