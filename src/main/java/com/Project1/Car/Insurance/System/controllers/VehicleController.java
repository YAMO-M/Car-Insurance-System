package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.UpdateVehicleDto;
import com.Project1.Car.Insurance.System.dtos.VehicleDto;
import com.Project1.Car.Insurance.System.services.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping("/{userId}")
    public VehicleDto addVehicle(@PathVariable UUID userId, @Valid @RequestBody VehicleDto vehicleDto){
       return vehicleService.addVehicle(vehicleDto,userId);
    }

//    @PutMapping("/{userId}")
//    public VehicleDto updateVehicle(@PathVariable UUID userId, @Valid @RequestBody UpdateVehicleDto updateVehicleDto ){
//        return vehicleService.updateVehicle(updateVehicleDto,userId);
//    }
//    @GetMapping("/{userId}")
//    public VehicleDto getVehicle(@PathVariable UUID userId){
//        return vehicleService.getVehicle(userId);
//    }
//    @DeleteMapping("/{userId}")
//    public VehicleDto updateVehicle(@PathVariable UUID userId){
//        return vehicleService.deleteVehicle(userId);
//    }
}
