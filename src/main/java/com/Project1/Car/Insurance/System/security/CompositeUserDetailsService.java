package com.Project1.Car.Insurance.System.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompositeUserDetailsService implements UserDetailsService {
    private final ClientDetailsService clientDetailsService;
    private final AdminDetailsService adminDetailsService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            return adminDetailsService.loadUserByUsername(username);
        }
        catch (UsernameNotFoundException e){
            return clientDetailsService.loadUserByUsername(username);

        }
    }
}
