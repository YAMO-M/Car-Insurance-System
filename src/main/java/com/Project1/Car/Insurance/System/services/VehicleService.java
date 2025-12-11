package com.Project1.Car.Insurance.System.services;

import com.Project1.Car.Insurance.System.dtos.UpdateVehicleDto;
import com.Project1.Car.Insurance.System.dtos.VehicleDto;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.entities.Vehicle;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import com.Project1.Car.Insurance.System.repositories.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;

    public VehicleDto addVehicle(VehicleDto vehicleDto, UUID userId){
        if(!clientRepository.existsById(userId)) throw new IllegalStateException("client does not exist");
        checkAccountCompletion(userId);

        Client client = clientRepository.findClientByClientId(userId);
        Vehicle vehicle = Vehicle.builder().client(client).build();
        vehicleRepository.save(vehicle);
        return  vehicleDto;
    }
    private void checkAccountCompletion(UUID userId){
        if(!clientRepository.findClientByClientId(userId).isProfileCompleted())
            throw new IllegalStateException("complete profile to continue");
    }

//    public VehicleDto updateVehicle(@Valid UpdateVehicleDto updateVehicleDto, UUID userId) {
//        if(!clientRepository.existsById(userId)) throw new IllegalStateException("client does not exist");
//        checkAccountCompletion(userId);
//
//
//
//
//
//
//    }


}
