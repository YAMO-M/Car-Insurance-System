package com.Project1.Car.Insurance.System.dtos;
public record VehicleRequest(
        String make,
        String model,
        String vin,
        Integer year,
        String licence_plate
) {
}
