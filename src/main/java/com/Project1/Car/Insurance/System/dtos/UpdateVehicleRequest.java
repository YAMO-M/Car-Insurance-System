package com.Project1.Car.Insurance.System.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateVehicleRequest(
        @NotNull
        UUID carId,

        @NotNull
        String licence_plate

) {
}
