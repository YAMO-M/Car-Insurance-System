package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.ClientDto;
import com.Project1.Car.Insurance.System.dtos.CompleteProfileDto;
import com.Project1.Car.Insurance.System.dtos.RegisterRequest;
import com.Project1.Car.Insurance.System.dtos.UpdateProfileDto;
import com.Project1.Car.Insurance.System.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest){
        clientService.register(registerRequest);
        return ResponseEntity.ok("Account Registered");
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping(path = "/complete-profile")
    public ResponseEntity<?> completeRegistration(@Valid @RequestBody CompleteProfileDto completeProfileDto, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        clientService.completeProfile(completeProfileDto,email);
        return ResponseEntity.ok("Profile Completed");
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        clientService.updateProfile(updateProfileDto,email);
        return ResponseEntity.ok("Profile Updated");
    }
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public ResponseEntity<?> getProfile( @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        ClientDto dto = clientService.getClient(email);
        return ResponseEntity.ok(dto);
    }


}
