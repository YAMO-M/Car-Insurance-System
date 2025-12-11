package com.Project1.Car.Insurance.System.repositories;

import com.Project1.Car.Insurance.System.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> getClientByEmail(String email);
    boolean existsClientByEmail(String email);

    Client findClientByClientId(UUID id);

    boolean existsClientByNationalId(String nationalId);
}
