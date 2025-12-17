package com.Project1.Car.Insurance.System.security;

import com.Project1.Car.Insurance.System.entities.Admin;
import com.Project1.Car.Insurance.System.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String adminEmail) throws UsernameNotFoundException {
        //Fetch the user
        Admin admin = adminRepository.getAdminByEmail(adminEmail);
        if(admin == null ) throw new UsernameNotFoundException("User not found");

        //Grant Authority
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + admin.getRole());

        return User
                .builder()
                .username(admin.getEmail())
                .password(admin.getPasskey())
                .authorities(authority)
                .build();
    }
}
