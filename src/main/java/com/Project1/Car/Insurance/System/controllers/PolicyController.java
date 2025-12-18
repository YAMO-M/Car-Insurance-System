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

import java.util.List;
import java.util.UUID;

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
        return ResponseEntity.status(201).body(dto);
    }
    @GetMapping("/{policyId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<PolicyResponse> getPolicy(@AuthenticationPrincipal UserDetails clientDetails, @PathVariable UUID policyId){
        String email = clientDetails.getUsername();
        PolicyResponse dto = policyService.getPolicy(policyId,email);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{policyId}/cancel")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> cancelPolicy(@AuthenticationPrincipal UserDetails clientDetails,@PathVariable UUID policyId){
        String email = clientDetails.getUsername();
        policyService.cancelPolicy(policyId,email);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{policyId}/renew")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> renewPolicy(@AuthenticationPrincipal UserDetails clientDetails,@PathVariable UUID policyId){
        String email = clientDetails.getUsername();
        policyService.renewPolicy(policyId,email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/expired")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<PolicyResponse>> getExpiredPolicies(@AuthenticationPrincipal UserDetails clientDetails){
        String email = clientDetails.getUsername();
        List<PolicyResponse> dto = policyService.getExpiredPolicies(email);
        return ResponseEntity.ok(dto);
    }
}
