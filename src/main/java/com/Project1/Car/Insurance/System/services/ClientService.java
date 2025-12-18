package com.Project1.Car.Insurance.System.services;


import com.Project1.Car.Insurance.System.dtos.*;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.entities.Policy;
import com.Project1.Car.Insurance.System.entities.PolicyStatus;
import com.Project1.Car.Insurance.System.entities.Roles;
import com.Project1.Car.Insurance.System.mappers.ClientMapper;
import com.Project1.Car.Insurance.System.mappers.PolicyMapper;
import com.Project1.Car.Insurance.System.mappers.VehicleMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        client.setRole(Roles.CLIENT);
        clientRepository.save(client);
    }

    @Transactional
    public CompleteProfileDto completeProfile(@Valid CompleteProfileDto completeProfileDto, String email) {
        checkIfClientExists(email);
        LocalDate clientDoB = calculateDoB(completeProfileDto.nationalId());
        if (!validateAge(clientDoB)) throw new IllegalStateException("Client must be at least 18 years old");
        if(clientRepository.existsClientByNationalId(completeProfileDto.nationalId())) throw new IllegalStateException("client with this national Id already exist");
        if(clientRepository.existsClientByPhoneNumber(completeProfileDto.phoneNumber())) throw new IllegalStateException("client with this phone number already exist");

        Client client = clientMapper
                .toCompletedClient(
                        clientRepository.getClientByEmail(email),
                        completeProfileDto
                );

        client.setDateOfBirth(clientDoB);

        clientRepository.save(client);
        return clientMapper.toCompletedProfileDto(client);
    }
    private LocalDate calculateDoB(String nationalId) {
        try {
            int year = Integer.parseInt(nationalId.substring(0, 2));
            int now = LocalDate.now().getYear() - 2000;

            if(year > now) year+=1900; else year+=2000;

            int month = Integer.parseInt(nationalId.substring(2, 4));
            if (month > 12 || month < 0) throw new IllegalStateException("Valid month required");

            int day = Integer.parseInt(nationalId.substring(4, 6));
            if (day > 31 || day < 0) throw new IllegalStateException("Valid day required");

            return LocalDate.of(year,month,day);


        }
        catch (NumberFormatException e){
            throw new IllegalStateException("Valid National Id required");
        }
    }
    boolean validateAge(LocalDate dob){
        return dob.plusYears(18).isBefore(LocalDate.now());
    }

    @Transactional
    public CompleteProfileDto updateProfile(@Valid UpdateProfileRequest updateProfileDto, String email) {
         checkIfClientExists(email);
         Client client = clientRepository.getClientByEmail(email);
         if(!(updateProfileDto.address() == null))
              client.setAddress(updateProfileDto.address());
         if(!(updateProfileDto.phoneNumber() == null))
              client.setPhoneNumber(updateProfileDto.phoneNumber());
         clientRepository.save(client);

         return  clientMapper.toCompletedProfileDto(client);
    }


    public ClientResponse getClient(String email) {
        checkIfClientExists(email);
        return clientMapper.toClientResponse(clientRepository.getClientByEmail(email));
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
    public void deActivateClient(String email) {
        checkIfClientExists(email);
        Client client = clientRepository.getClientByEmail(email);
        List<Policy> policies = client
                .getPolicies()
                .stream()
                .filter(
                        policy ->
                                policy.getPolicyStatus()
                                        .equals(PolicyStatus.ACTIVE)
                )
                .toList();
        if(!policies.isEmpty())
            throw new IllegalStateException("Client currently have active policies");
        client.setEnabled(false);
        clientRepository.save(client);
    }
    @Transactional
    public ClientResponse activateClient(String email){
        checkIfClientExists(email);
        Client client = clientRepository.getClientByEmail(email);
        client.setEnabled(true);
        return clientMapper
                .toClientResponse(clientRepository.save(client));
   }


}

