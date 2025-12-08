package com.Project1.Car.Insurance.System.services;


import com.Project1.Car.Insurance.System.dtos.CompleteProfileDto;
import com.Project1.Car.Insurance.System.dtos.RegisterDto;
import com.Project1.Car.Insurance.System.dtos.UpdateProfileDto;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.mappers.ClientMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    @Transactional
    public RegisterDto register(@Valid RegisterDto dto){
        if(clientRepository.existsClientByEmail(dto.email())) throw new IllegalStateException("client already exist");

        Client client = clientRepository
                .save(
                        clientMapper
                        .toClient(dto)
                );
        return clientMapper.toRegisterDto(client);
    }

   @Transactional
    public CompleteProfileDto completeProfile(@Valid CompleteProfileDto completeProfileDto, UUID id) {
        if(!clientRepository.existsById(id)) throw new IllegalStateException("client does not exist");
        if(clientRepository.existsClientByIdNumber(completeProfileDto.idNumber())) throw new IllegalStateException("client already exist");
        Client client = clientMapper
                .toCompletedClient(
                        clientRepository.findClientByClientId(id),
                        completeProfileDto
                );


        clientRepository.save(client);
        return clientMapper.toCompletedProfileDto(client);
    }

    @Transactional
    public CompleteProfileDto updateProfile(@Valid UpdateProfileDto updateProfileDto, UUID id) {
        if(!clientRepository.existsById(id)) throw new IllegalStateException("client does not exist");
         Client client = clientRepository.findClientByClientId(id);
         client.setAddress(updateProfileDto.address());
         client.setPhoneNumber(updateProfileDto.phoneNumber());
         clientRepository.save(client);
         return  clientMapper.toCompletedProfileDto(client);
    }
}
