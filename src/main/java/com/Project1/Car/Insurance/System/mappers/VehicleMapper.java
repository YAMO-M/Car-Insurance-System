package com.Project1.Car.Insurance.System.mappers;

import com.Project1.Car.Insurance.System.dtos.VehicleDto;
import com.Project1.Car.Insurance.System.entities.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    public Vehicle toVehicle(VehicleDto dto){
        return Vehicle
                .builder()
                .model(dto.model())
                .vin(dto.vin())
                .licence_plate(dto.licence_plate())
                .year(dto.year())
                .make(dto.make())
                .build();
    }
}
