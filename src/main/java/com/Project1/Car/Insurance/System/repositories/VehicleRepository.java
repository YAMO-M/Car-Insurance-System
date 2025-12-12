package com.Project1.Car.Insurance.System.repositories;

import com.Project1.Car.Insurance.System.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Vehicle findByVehicleId(UUID vehicleId);

    boolean existsVehicleByVin(String vin);
}
