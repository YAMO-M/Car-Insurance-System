package com.Project1.Car.Insurance.System.services;

import com.Project1.Car.Insurance.System.dtos.ClientDto;
import com.Project1.Car.Insurance.System.dtos.PolicyResponse;
import com.Project1.Car.Insurance.System.mappers.ClientMapper;
import com.Project1.Car.Insurance.System.mappers.PolicyMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import com.Project1.Car.Insurance.System.repositories.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PolicyRepository policyRepository;
    private final PolicyMapper policyMapper;

    public List<ClientDto> getAllClients() {
        return clientRepository
                .findAll()
                .stream()
                .map(clientMapper::toClientDto)
                .toList();
    }
    public List<PolicyResponse> getAllPolicies(){
        return policyRepository
                .findAll()
                .stream()
                .map(policyMapper::toPolicyResponse)
                .toList();
    }
}
