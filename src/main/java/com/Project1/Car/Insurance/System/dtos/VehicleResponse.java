package com.Project1.Car.Insurance.System.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record VehicleResponse(
        UUID vehicleId,
        String make,
        String model,
        String vin,
        Integer year,
        String licence_plate
) {
}
