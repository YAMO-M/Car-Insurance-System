package com.Project1.Car.Insurance.System.dtos;

import com.Project1.Car.Insurance.System.entities.PolicyStatus;
import com.Project1.Car.Insurance.System.entities.PolicyType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record PolicyResponse(
        UUID policyId,
        PolicyType policyType,
        LocalDate startDate,
        LocalDate endDate,
        PolicyStatus policyStatus,
        Double premiumAmount
) {
}
