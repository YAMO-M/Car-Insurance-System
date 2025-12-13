package com.Project1.Car.Insurance.System.services;

import com.Project1.Car.Insurance.System.repositories.AdminRepository;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;

    public void deActivateClient(UUID id){
        changeAccountStatus(id, false);
    }
    public void ActivateClient(UUID id){
        changeAccountStatus(id,true);
    }
    private void changeAccountStatus(UUID id, boolean status) {
        if (!clientRepository.existsById(id)) throw new IllegalStateException("client does not exist");
        //Client client = clientRepository.id);
      //  client.setAccountActive(status);
       // clientRepository.save(client);
    }


    
    
    

}
