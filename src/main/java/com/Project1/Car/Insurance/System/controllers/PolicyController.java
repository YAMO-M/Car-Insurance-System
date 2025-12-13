package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.PolicyRequest;
import com.Project1.Car.Insurance.System.dtos.PolicyResponse;
import com.Project1.Car.Insurance.System.services.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/v1/policies")
@RequiredArgsConstructor
public class PolicyController {
    private final PolicyService policyService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<PolicyResponse> addPolicy(@Valid @RequestBody PolicyRequest policyRequest, @AuthenticationPrincipal UserDetails clientDetails){
        String email = clientDetails.getUsername();
        PolicyResponse dto = policyService.addPolicy(policyRequest, email);
        return ResponseEntity.ok(dto);
    }
}
