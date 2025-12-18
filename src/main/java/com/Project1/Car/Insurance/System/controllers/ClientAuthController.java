package com.Project1.Car.Insurance.System.controllers;

import com.Project1.Car.Insurance.System.dtos.AuthRequest;
import com.Project1.Car.Insurance.System.dtos.AuthResponse;
import com.Project1.Car.Insurance.System.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/client")
@RequiredArgsConstructor
public class ClientAuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authRequest.email(),
                                authRequest.password()
                        )
                );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(!userDetails.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_CLIENT"))) {
            return ResponseEntity.status(401).body(null);
        }
        String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }


}
