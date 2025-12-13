package com.Project1.Car.Insurance.System.services;

import com.Project1.Car.Insurance.System.dtos.PolicyRequest;
import com.Project1.Car.Insurance.System.dtos.PolicyResponse;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.entities.Policy;
import com.Project1.Car.Insurance.System.entities.PolicyStatus;
import com.Project1.Car.Insurance.System.entities.Vehicle;
import com.Project1.Car.Insurance.System.mappers.PolicyMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import com.Project1.Car.Insurance.System.repositories.PolicyRepository;
import com.Project1.Car.Insurance.System.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PolicyService {
    private final PolicyRepository policyRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;
    private final PolicyMapper policyMapper;


    public PolicyResponse addPolicy(PolicyRequest policyRequest, String email){

        validateClient(email,policyRequest.vehicleId());

        Client client = clientRepository.getClientByEmail(email);
        Vehicle vehicle = vehicleRepository.findByVehicleId(policyRequest.vehicleId());
        if(!vehicle.getClient().getEmail().equals(email))
            throw new IllegalStateException("Forbidden Access");
        boolean isVehicleFound  = false;

        for(Vehicle i : client.getVehicles()) {
            if (i.getVehicleId().equals(vehicle.getVehicleId())){
                isVehicleFound = true;
                break;
            }
        }

        if(!isVehicleFound)
            throw new IllegalStateException("Client: "+client.getClientId()+ " does not own Vehicle:" +vehicle.getVehicleId());

        if(policyRepository.existsPolicyByVehicle_VehicleId(policyRequest.vehicleId())) throw new IllegalStateException("vehicle already has policy");
        LocalDate now = LocalDate.now();
        Policy policy = Policy
                .builder()
                .vehicle(vehicle)
                .client(client)
                .policyType(policyRequest.policyType())
                .startDate(now)
                .endDate(now.plusMonths(1))
                .policyStatus(PolicyStatus.ACTIVE)
                .premiumAmount(100.00)
                .build();

        client.getPolicies().add(policy);


        return policyMapper
                .toPolicyResponse(policyRepository.save(policy)
                );
    }

    private void validateClient(String email,UUID vehicleId){
        if(!clientRepository.existsClientByEmail(email))
            throw new IllegalStateException("client does not exist");
        if(!clientRepository.getClientByEmail(email).isProfileCompleted())
            throw new IllegalStateException("complete profile to continue");
        if(!vehicleRepository.existsById(vehicleId))
            throw new IllegalStateException("vehicle does not exist");

    }
}
