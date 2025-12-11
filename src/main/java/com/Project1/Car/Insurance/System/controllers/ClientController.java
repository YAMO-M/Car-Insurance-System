package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.ClientDto;
import com.Project1.Car.Insurance.System.dtos.CompleteProfileDto;
import com.Project1.Car.Insurance.System.dtos.RegisterDto;
import com.Project1.Car.Insurance.System.dtos.UpdateProfileDto;
import com.Project1.Car.Insurance.System.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(path = "/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto){
        RegisterDto dto = clientService.register(registerDto);
        return ResponseEntity.ok(dto);
    }
    @PutMapping(path = "/{client_id}/complete-profile")
    public ResponseEntity<?> completeRegistration(@Valid @RequestBody CompleteProfileDto completeProfileDto, @PathVariable UUID client_id){
        CompleteProfileDto dto = clientService.completeProfile(completeProfileDto,client_id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(path = "/{client_id}")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto, @PathVariable UUID client_id){
        CompleteProfileDto dto = clientService.updateProfile(updateProfileDto,client_id);
        return ResponseEntity.ok(dto);
    }

}
