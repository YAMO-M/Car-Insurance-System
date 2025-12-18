package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.ClientResponse;
import com.Project1.Car.Insurance.System.dtos.PolicyResponse;
import com.Project1.Car.Insurance.System.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/clients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientResponse>> getAllClients(){
        List<ClientResponse> clientDtos = adminService.getAllClients();
        return ResponseEntity.ok(clientDtos);
    }
    @GetMapping("/policies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PolicyResponse>> getAllPolicies(){
        List<PolicyResponse> policyResponses = adminService.getAllPolicies();
        return ResponseEntity.ok(policyResponses);
    }



}
