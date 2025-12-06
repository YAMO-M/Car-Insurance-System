package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.CompleteProfileDto;
import com.Project1.Car.Insurance.System.dtos.RegisterDto;
import com.Project1.Car.Insurance.System.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto){
        RegisterDto dto = clientService.register(registerDto);
        return ResponseEntity.ok(dto);
    }
    @PutMapping(path = "/completeprofile/{id}")
    public ResponseEntity<?> register(@Valid @RequestBody CompleteProfileDto completeProfileDto,@PathVariable UUID id){
        CompleteProfileDto dto = clientService.completeProfile(completeProfileDto,id);
        return ResponseEntity.ok(dto);
    }
}
