package com.Project1.Car.Insurance.System.security;

import com.Project1.Car.Insurance.System.entities.Client;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClientDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;


    @Override
    public UserDetails loadUserByUsername(String clientEmail) throws UsernameNotFoundException {
        //Fetch the user
        Client client = clientRepository.getClientByEmail(clientEmail);
        if(client == null ) throw new UsernameNotFoundException("User not found");

        //Grant Authority
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + client.getRole());

        return User
                .builder()
                .username(client.getEmail())
                .password(client.getPassword())
                .authorities(authority)
                .disabled(!client.isEnabled()) // if false then spring blocks the login
                .build();
    }
}
