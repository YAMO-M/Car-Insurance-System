package com.Project1.Car.Insurance.System.services;

import com.Project1.Car.Insurance.System.dtos.UpdateVehicleRequest;
import com.Project1.Car.Insurance.System.dtos.VehicleRequest;
import com.Project1.Car.Insurance.System.dtos.VehicleResponse;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.entities.Vehicle;
import com.Project1.Car.Insurance.System.mappers.VehicleMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import com.Project1.Car.Insurance.System.repositories.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class VehicleService{
    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public VehicleResponse addVehicle(VehicleRequest vehicleRequest, String email){
        checkIfClientExists(email);
        checkAccountCompletion(email);

        Client client = clientRepository.getClientByEmail(email);
        Vehicle vehicle = vehicleMapper.toVehicle(vehicleRequest);

        if(vehicle.getYear() < LocalDate.now().minusYears(15).getYear()) throw new IllegalStateException(" Vehicle must be less than 15 years old");
        if(vehicle.getYear() > LocalDate.now().getYear()) throw new  IllegalStateException("Car year invalid");
        if(vehicleRepository.existsVehicleByVin(vehicle.getVin()))
            throw  new IllegalStateException("vehicle already has a owner");

        vehicle.setClient(client);
        client.getVehicles().add(vehicle);

        return vehicleMapper
                .toVehicleResponse(
                        vehicleRepository.save(vehicle)
                );
    }


    public VehicleResponse updateVehicle(@Valid UpdateVehicleRequest updateVehicleDto, String email) {
        checkIfClientExists(email);

        Vehicle vehicle = validateVehicle(updateVehicleDto.carId(),email);
        vehicle.setLicence_plate(updateVehicleDto.licence_plate());
        return vehicleMapper
                .toVehicleResponse(
                        vehicleRepository.save(vehicle)
                );
    }


    private void checkIfClientExists(String email){
        if(!clientRepository.existsClientByEmail(email))
            throw new IllegalStateException("client does not exist");
    }

    public VehicleResponse getVehicle(UUID vehicleId, String email) {
        checkIfClientExists(email);
        Vehicle vehicle = validateVehicle(vehicleId,email);
        return vehicleMapper.toVehicleResponse(vehicle);
    }

    public void deleteVehicle(UUID vehicleId, String email) {
        Vehicle vehicle = validateVehicle(vehicleId,email);
        if (vehicle.getPolicy() != null) throw new IllegalStateException("Vehicle currently has a policy");
        vehicleRepository.delete(vehicle);
    }

    private Vehicle validateVehicle(UUID vehicleId,String email){
        Vehicle vehicle = vehicleRepository.findByVehicleId(vehicleId);
        if(vehicle == null) throw new IllegalStateException("Vehicle not found");
        if(!vehicle.getClient().getEmail().equals(email)) throw new IllegalStateException("Forbidden access");


        return vehicle;
    }
    private void checkAccountCompletion(String email){
        if(!clientRepository.getClientByEmail(email).isProfileCompleted())
            throw new IllegalStateException("complete profile to continue");
    }
}
