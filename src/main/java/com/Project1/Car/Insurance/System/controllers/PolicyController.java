package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.PolicyDto;
import com.Project1.Car.Insurance.System.services.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping( "/api/v1/policies")
@RequiredArgsConstructor
public class PolicyController {
    private final PolicyService policyService;

    @PostMapping("/user/{id}")
    public PolicyDto addPolicy(@Valid @RequestBody PolicyDto policyDto, @PathVariable UUID id){
           return policyService.addPolicy(policyDto,id);
    }
}
