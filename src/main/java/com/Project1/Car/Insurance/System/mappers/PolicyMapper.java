package com.Project1.Car.Insurance.System.mappers;

import com.Project1.Car.Insurance.System.dtos.PolicyResponse;
import com.Project1.Car.Insurance.System.entities.Policy;
import org.springframework.stereotype.Service;

@Service
public class PolicyMapper {

    public PolicyResponse toPolicyResponse(Policy policy) {
        return PolicyResponse
                .builder()
                .policyId(policy.getPolicyId())
                .policyStatus(policy.getPolicyStatus())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .policyType(policy.getPolicyType())
                .premiumAmount(policy.getPremiumAmount())
                .build();
    }
}
