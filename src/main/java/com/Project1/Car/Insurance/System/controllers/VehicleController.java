package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.UpdateVehicleDto;
import com.Project1.Car.Insurance.System.dtos.VehicleDto;
import com.Project1.Car.Insurance.System.services.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public VehicleDto addVehicle(@Valid @RequestBody VehicleDto vehicleDto, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        return vehicleService.addVehicle(vehicleDto,email);
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
