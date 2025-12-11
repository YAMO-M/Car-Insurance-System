package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PutMapping(path = "/users/deactivate/{id}")
    public ResponseEntity<?> deActivateClient(@PathVariable UUID id){
        adminService.deActivateClient(id);
        return ResponseEntity.ok(" Account deActivated");
    }
    @PutMapping(path = "/users/activate/{id}")
    public ResponseEntity<?> ActivateClient(@PathVariable UUID id){
        adminService.ActivateClient(id);
        return ResponseEntity.ok(" Account Activated");
    }
}
