package com.Project1.Car.Insurance.System.services;

import com.Project1.Car.Insurance.System.dtos.PolicyDto;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.entities.Policy;
import com.Project1.Car.Insurance.System.entities.Vehicle;
import com.Project1.Car.Insurance.System.mappers.PolicyMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import com.Project1.Car.Insurance.System.repositories.PolicyRepository;
import com.Project1.Car.Insurance.System.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PolicyService {
    private final PolicyRepository policyRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;
    private final PolicyMapper policyMapper;


    public PolicyDto addPolicy(PolicyDto policyDto, UUID id){
        if(!clientRepository.existsById(id)) throw new IllegalStateException("client does not exist");
        checkAccountCompletion(id);
        if(!vehicleRepository.existsById(policyDto.carId())) throw new IllegalStateException("vehicle does not exist");


        Client client = clientRepository.findClientByClientId(id);
        Vehicle vehicle = vehicleRepository.findByVehicleId(policyDto.carId());
        boolean isVehicleFound  = false;
        for(Vehicle i : client.getVehicles()){
            if(i.getVehicleId().equals(vehicle.getVehicleId())){
                isVehicleFound = true;
            }
        }
        if(!isVehicleFound) throw new IllegalStateException("Client: "+client.getClientId()+ " does not own Vehicle:" +vehicle.getVehicleId());

        if(policyRepository.existsPolicyByVehicle_VehicleId(policyDto.carId())) throw new IllegalStateException("vehicle already has policy");
        LocalDate now = LocalDate.now();
        Policy policy = Policy
                .builder()
                .vehicle(vehicle)
                .policyType(policyDto.policyType())
                .startDate(now)
                .endDate(now.plusMonths(1))
                .policyStatus(true)
                .premiumAmount(100.00)
                .build();

        client.getPolicies().add(policy);
        policyRepository.save(policy);
        clientRepository.save(client);
        return policyMapper.toPolicyDto(policy);
    }

    private void checkAccountCompletion(UUID id){
        if(!clientRepository.findClientByClientId(id).isProfileCompleted())
            throw new IllegalStateException("complete profile to continue");
    }
}
