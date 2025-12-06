package com.Project1.Car.Insurance.System.mappers;

import com.Project1.Car.Insurance.System.dtos.CompleteProfileDto;
import com.Project1.Car.Insurance.System.dtos.RegisterDto;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.entities.Roles;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toClient(RegisterDto dto){
        return Client
                .builder()
                .email(dto.email())
                .password(dto.password())
                .build();
    }
    public Client toCompletedClient(Client client,CompleteProfileDto completeProfileDto){
        client.setAddress(completeProfileDto.address());
        client.setFName(completeProfileDto.fName());
        client.setLName(completeProfileDto.lName());
        client.setIdNumber(completeProfileDto.idNumber());
        client.setPhoneNumber(completeProfileDto.phoneNumber());
        client.setProfileCompleted(true);
        client.setAccountActive(true);
        client.setRole(Roles.CLIENT);
        return client;
    }
    public RegisterDto toRegisterDto(Client client){
        return RegisterDto
                .builder()
                .email(client.getEmail())
                .password(client.getPassword())
                .build();
    }
    public CompleteProfileDto toCompletedProfileDto(Client client){
        return CompleteProfileDto
                .builder()
                .address(client.getAddress())
                .fName(client.getFName())
                .lName(client.getLName())
                .phoneNumber(client.getPhoneNumber())
                .idNumber(client.getIdNumber())
                .build();
    }

}
