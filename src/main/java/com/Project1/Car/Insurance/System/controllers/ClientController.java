package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.*;
import com.Project1.Car.Insurance.System.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerClient(@Valid @RequestBody RegisterRequest registerRequest){
        clientService.register(registerRequest);
        return ResponseEntity.status(201).body("Account Registered");
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(path = "/complete-profile")
    public ResponseEntity<CompleteProfileDto> completeClientRegistration(@Valid @RequestBody CompleteProfileDto completeProfileDto, @AuthenticationPrincipal UserDetails clientDetails){
        String email = clientDetails.getUsername();
        CompleteProfileDto dto = clientService.completeProfile(completeProfileDto,email);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping
    public ResponseEntity<CompleteProfileDto> updateClientProfile(@Valid @RequestBody UpdateProfileRequest updateProfileRequest, @AuthenticationPrincipal UserDetails clientDetails){
        String email = clientDetails.getUsername();
        CompleteProfileDto dto = clientService.updateProfile(updateProfileRequest,email);
        return ResponseEntity.ok(dto);
    }
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public ResponseEntity<ClientResponse> getClientProfile( @AuthenticationPrincipal UserDetails clientDetails){
        String email = clientDetails.getUsername();
        ClientResponse dto = clientService.getClient(email);
        return ResponseEntity.ok(dto);
    }
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping(path = "/vehicles")
    public ResponseEntity<List<VehicleResponse>> getAllClient_Vehicles(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        List<VehicleResponse> dtos = clientService.getAllVehicles(email);
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deActivateClient(@AuthenticationPrincipal UserDetails clientDetails){
        String email = clientDetails.getUsername();
        clientService.deActivateClient(email);
        return ResponseEntity
                .noContent()
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ClientResponse> activateClient(@AuthenticationPrincipal UserDetails clientDetails){
        String email = clientDetails.getUsername();
        ClientResponse dto = clientService.activateClient(email);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping(path = "/policies")
    public ResponseEntity<List<PolicyResponse>> getAllClient_Policies(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        List<PolicyResponse> dtos = clientService.getAllPolicies(email);
        return ResponseEntity.ok(dtos);
    }

}
