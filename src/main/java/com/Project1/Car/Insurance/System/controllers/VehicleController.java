package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.VehicleDto;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.services.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicle/user")
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping("/{userId}")
    public VehicleDto addVehicle(@PathVariable UUID userId, @Valid @RequestBody VehicleDto vehicleDto){
       return vehicleService.addVehicle(vehicleDto,userId);
    }
}
