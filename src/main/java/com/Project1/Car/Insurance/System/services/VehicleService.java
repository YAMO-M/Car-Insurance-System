package com.Project1.Car.Insurance.System.services;

import com.Project1.Car.Insurance.System.dtos.UpdateVehicleDto;
import com.Project1.Car.Insurance.System.dtos.VehicleDto;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.entities.Vehicle;
import com.Project1.Car.Insurance.System.mappers.VehicleMapper;
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
    private final VehicleMapper vehicleMapper;

    public VehicleDto addVehicle(VehicleDto vehicleDto, String email){
        if(!clientRepository.existsClientByEmail(email)) throw new IllegalStateException("client does not exist");
        checkAccountCompletion(email);

        Client client = clientRepository.getClientByEmail(email);
        Vehicle vehicle = vehicleMapper.toVehicle(vehicleDto);
        vehicle.setClient(client);
        client.getVehicles().add(vehicle);
        clientRepository.save(client);
        return vehicleDto;
    }
    private void checkAccountCompletion(String email){
        if(!clientRepository.getClientByEmail(email).isProfileCompleted())
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
