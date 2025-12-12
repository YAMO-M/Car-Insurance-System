package com.Project1.Car.Insurance.System.mappers;

import com.Project1.Car.Insurance.System.dtos.VehicleRequest;
import com.Project1.Car.Insurance.System.dtos.VehicleResponse;
import com.Project1.Car.Insurance.System.entities.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    public Vehicle toVehicle(VehicleRequest dto){
        return Vehicle
                .builder()
                .model(dto.model())
                .vin(dto.vin())
                .licence_plate(dto.licence_plate())
                .year(dto.year())
                .make(dto.make())
                .build();
    }
    public VehicleResponse toVehicleResponse(Vehicle vehicle){
        return VehicleResponse
                .builder()
                .vehicleId(vehicle.getVehicleId())
                .model(vehicle.getModel())
                .vin(vehicle.getVin())
                .licence_plate(vehicle.getLicence_plate())
                .year(vehicle.getYear())
                .make(vehicle.getMake())
                .build();
    }
}
