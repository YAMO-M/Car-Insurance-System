package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.UpdateVehicleRequest;
import com.Project1.Car.Insurance.System.dtos.VehicleRequest;
import com.Project1.Car.Insurance.System.dtos.VehicleResponse;
import com.Project1.Car.Insurance.System.services.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<VehicleResponse> addVehicle(@Valid @RequestBody VehicleRequest vehicleRequest, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        VehicleResponse dto = vehicleService.addVehicle(vehicleRequest,email);
        return ResponseEntity.status(201).body(dto) ;
    }

    @PutMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<VehicleResponse> updateVehicle(@Valid @RequestBody UpdateVehicleRequest updateVehicleRequest, @AuthenticationPrincipal UserDetails userDetails ){
        String email = userDetails.getUsername();
        VehicleResponse dto = vehicleService.updateVehicle(updateVehicleRequest,email);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/{vehicleId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<VehicleResponse> getVehicle(@AuthenticationPrincipal UserDetails userDetails,@PathVariable UUID vehicleId){
        String email = userDetails.getUsername();
        VehicleResponse dto =  vehicleService.getVehicle(vehicleId,email);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{vehicleId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> deleteVehicle(@AuthenticationPrincipal UserDetails clientDetails,@PathVariable UUID vehicleId){
        String email = clientDetails.getUsername();
        vehicleService.deleteVehicle(vehicleId,email);
        return ResponseEntity.noContent().build();
    }




}
