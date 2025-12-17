package com.Project1.Car.Insurance.System.repositories;

import com.Project1.Car.Insurance.System.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Admin getAdminByEmail(String clientEmail);
}
