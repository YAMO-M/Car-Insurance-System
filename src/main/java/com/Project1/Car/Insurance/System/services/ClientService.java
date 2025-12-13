package com.Project1.Car.Insurance.System.services;


import com.Project1.Car.Insurance.System.dtos.*;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.mappers.ClientMapper;
import com.Project1.Car.Insurance.System.mappers.PolicyMapper;
import com.Project1.Car.Insurance.System.mappers.VehicleMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final VehicleMapper vehicleMapper;
    private final PolicyMapper policyMapper;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(@Valid RegisterRequest dto){
        if(clientRepository.existsClientByEmail(dto.email()))
            throw new IllegalStateException("client with email already exist");

        Client client = clientMapper.toClient(dto);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    @Transactional
    public CompleteProfileDto completeProfile(@Valid CompleteProfileDto completeProfileDto, String email) {
        checkIfClientExists(email);
        if(clientRepository.existsClientByNationalId(completeProfileDto.nationalId())) throw new IllegalStateException("client with this national Id already exist");
        if(clientRepository.existsClientByPhoneNumber(completeProfileDto.phoneNumber())) throw new IllegalStateException("client with this phone number already exist");
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
         checkIfClientExists(email);
         Client client = clientRepository.getClientByEmail(email);
         if(!(updateProfileDto.address() == null))
              client.setAddress(updateProfileDto.address());
         if(!(updateProfileDto.phoneNumber() == null))
              client.setPhoneNumber(updateProfileDto.phoneNumber());
         clientRepository.save(client);

         return  clientMapper.toCompletedProfileDto(client);
    }

    @Transactional
    public ClientDto getClient(String email) {
        checkIfClientExists(email);
        return clientMapper.toClientDto(clientRepository.getClientByEmail(email));
    }
    public List<PolicyResponse> getAllPolicies(String email) {
        checkIfClientExists(email);
        return clientRepository
                .getClientByEmail(email)
                .getPolicies()
                .stream()
                .map(policyMapper::toPolicyResponse)
                .toList();
    }

    public List<VehicleResponse> getAllVehicles(String email) {
        checkIfClientExists(email);
        return clientRepository
                .getClientByEmail(email)
                .getVehicles()
                .stream()
                .map(vehicleMapper::toVehicleResponse)
                .toList();
    }
    private void checkIfClientExists(String email){
        if(!clientRepository.existsClientByEmail(email))
            throw new IllegalStateException("client does not exist");
    }

    @Transactional
    public void deleteClient(String email) {
        checkIfClientExists(email);

        Client client = clientRepository.getClientByEmail(email);
        client.setAccountActive(false);

        clientRepository.save(client);
    }


}

