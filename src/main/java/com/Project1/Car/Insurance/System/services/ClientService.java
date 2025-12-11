package com.Project1.Car.Insurance.System.services;


import com.Project1.Car.Insurance.System.dtos.ClientDto;
import com.Project1.Car.Insurance.System.dtos.CompleteProfileDto;
import com.Project1.Car.Insurance.System.dtos.RegisterRequest;
import com.Project1.Car.Insurance.System.dtos.UpdateProfileDto;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.mappers.ClientMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(@Valid RegisterRequest dto){
        if(clientRepository.existsClientByEmail(dto.email())) throw new IllegalStateException("client already exist");

        Client client = clientMapper.toClient(dto);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    @Transactional
    public CompleteProfileDto completeProfile(@Valid CompleteProfileDto completeProfileDto, String email) {
        if(!clientRepository.existsClientByEmail(email)) throw new IllegalStateException("client does not exist");
        if(clientRepository.existsClientByNationalId(completeProfileDto.nationalId())) throw new IllegalStateException("client already exist");
        Client client = clientMapper
                .toCompletedClient(
                        clientRepository.getClientByEmail(email),
                        completeProfileDto
                );


        clientRepository.save(client);
        return clientMapper.toCompletedProfileDto(client);
    }

    @Transactional
    public CompleteProfileDto updateProfile(@Valid UpdateProfileDto updateProfileDto, String email) {
        if(!clientRepository.existsClientByEmail(email))
            throw new IllegalStateException("client does not exist");

         Client client = clientRepository.getClientByEmail(email);
         client.setAddress(updateProfileDto.address());
         client.setPhoneNumber(updateProfileDto.phoneNumber());
         clientRepository.save(client);

         return  clientMapper.toCompletedProfileDto(client);
    }

    @Transactional
    public ClientDto getClient(String email) {
        if(!clientRepository.existsClientByEmail(email))
            throw new IllegalStateException("client does not exist");

        return clientMapper.toClientDto(clientRepository.getClientByEmail(email));
    }
}
