package com.Project1.Car.Insurance.System.mappers;

import com.Project1.Car.Insurance.System.dtos.ClientDto;
import com.Project1.Car.Insurance.System.dtos.CompleteProfileDto;
import com.Project1.Car.Insurance.System.dtos.RegisterRequest;
import com.Project1.Car.Insurance.System.entities.Client;
import org.springframework.stereotype.Component;


@Component
public class ClientMapper {
    public Client toClient(RegisterRequest dto){
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
        client.setNationalId(completeProfileDto.nationalId());
        client.setPhoneNumber(completeProfileDto.phoneNumber());
        client.setProfileCompleted(true);
        client.setAccountActive(true);
        return client;
    }

    public CompleteProfileDto toCompletedProfileDto(Client client){
        return CompleteProfileDto
                .builder()
                .address(client.getAddress())
                .fName(client.getFName())
                .lName(client.getLName())
                .phoneNumber(client.getPhoneNumber())
                .nationalId(client.getNationalId())
                .build();
    }

    public ClientDto toClientDto(Client client) {
        return ClientDto
                .builder()
                .fName(client.getFName())
                .lName(client.getLName())
                .email(client.getEmail())
                .nationalId(client.getNationalId())
                .address(client.getAddress())
                .phoneNumber(client.getPhoneNumber())
                .profileCompleted(client.isProfileCompleted())
                .build();
    }
}
