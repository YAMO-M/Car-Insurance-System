package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.AuthRequest;
import com.Project1.Car.Insurance.System.dtos.AuthResponse;
import com.Project1.Car.Insurance.System.security.AdminDetailsService;
import com.Project1.Car.Insurance.System.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/admin")
@RequiredArgsConstructor
public class AdminAuthController {
    private final AdminDetailsService adminDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest authRequest){
        try {
            // manually load the admin
            UserDetails adminDetails = adminDetailsService.loadUserByUsername(authRequest.email());

            // manually check password
            if (!passwordEncoder.matches(authRequest.password(), adminDetails.getPassword())) {
                return ResponseEntity.status(401).body(null);
            }

            if (!adminDetails.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return ResponseEntity.status(401).body(null);
            }
            String jwt = jwtService.generateToken(adminDetails);
            return ResponseEntity.ok(new AuthResponse(jwt));
        }
        catch (UsernameNotFoundException e){
            return ResponseEntity.status(401).body(null);
        }
    }


}
