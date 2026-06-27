package com.Project1.Car.Insurance.System.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CompositeUserDetailsService implements UserDetailsService {
    private final ClientDetailsService clientDetailsService;
    private final AdminDetailsService adminDetailsService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         try{
            return clientDetailsService.loadUserByUsername(username);

        }
        catch (UsernameNotFoundException e){
            try {
                return adminDetailsService.loadUserByUsername(username);
            }catch (UsernameNotFoundException ex){
                throw new UsernameNotFoundException("User not found "+username);
            }
        }
    }
}
