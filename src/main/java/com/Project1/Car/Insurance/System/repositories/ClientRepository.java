package com.Project1.Car.Insurance.System.repositories;

import com.Project1.Car.Insurance.System.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client getClientByEmail(String email);
    boolean existsClientByEmail(String email);
    boolean existsClientByNationalId(String nationalId);
    boolean existsClientByPhoneNumber(String s);
    @Query("select c.profileCompleted from Client c where c.email = :email")
    boolean isProfileCompleted(@Param("email") String email);
}
