package com.Project1.Car.Insurance.System.mappers;

import com.Project1.Car.Insurance.System.dtos.PolicyDto;
import com.Project1.Car.Insurance.System.entities.Policy;
import org.springframework.stereotype.Service;

@Service
public class PolicyMapper {
    public PolicyDto toPolicyDto(Policy policy){
        return new PolicyDto(policy.getPolicyType(),policy.getVehicle().getVehicleId());
    }
}
