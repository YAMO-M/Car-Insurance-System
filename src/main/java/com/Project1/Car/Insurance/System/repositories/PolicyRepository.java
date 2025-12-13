package com.Project1.Car.Insurance.System.repositories;

import com.Project1.Car.Insurance.System.entities.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, UUID>{

    boolean existsPolicyByVehicle_VehicleId(UUID vehicleId);

    Policy findPoliciesByPolicyId(UUID policyId);
}
