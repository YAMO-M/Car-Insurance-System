package com.Project1.Car.Insurance.System.services;

import com.Project1.Car.Insurance.System.dtos.ClientResponse;
import com.Project1.Car.Insurance.System.dtos.VehicleRequest;
import com.Project1.Car.Insurance.System.dtos.VehicleResponse;
import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.entities.Vehicle;
import com.Project1.Car.Insurance.System.mappers.PolicyMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import com.Project1.Car.Insurance.System.repositories.PolicyRepository;
import com.Project1.Car.Insurance.System.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {
    // create mocks/ fake versions
    @Mock
    private  PolicyRepository policyRepository;
    @Mock
    private  ClientRepository clientRepository;
    @Mock
    private  VehicleRepository vehicleRepository;
    @Mock
    private  PolicyMapper policyMapper;

    @InjectMocks // inject the mocks that are created
    private PolicyService policyService;

    // reused data
    private Client testClient;
    private ClientResponse testClientResponse;
    

    private Vehicle testVehicle;
    private VehicleRequest testVehicleRequest;
    private VehicleResponse testVehicleResponse;

    @BeforeEach
    void setUp() {

    }

    @Test
    void addPolicy() {
    }

    @Test
    void getPolicy() {
    }

    @Test
    void cancelPolicy() {
    }

    @Test
    void renewPolicy() {
    }
}