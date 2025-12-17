package com.Project1.Car.Insurance.System.config;

import com.Project1.Car.Insurance.System.security.AdminDetailsService;
import com.Project1.Car.Insurance.System.security.ClientDetailsService;
import com.Project1.Car.Insurance.System.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity // allows @PreAuthorise ..etc
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ClientDetailsService clientDetailsService;
    private final AdminDetailsService adminDetailsService;

    // my security rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception
    {
        http
                .csrf(csrf -> csrf.disable())
                .cors( cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/v1/clients/register").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no http sessions needed, jwt carry the auth info
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);// insert my filter in spring chain of filters
        return http.build();

    }

    @Bean // prevent other websites to make requests
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration  configuration  = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080","http://localhost:63342"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS")); // browser sends options first to check id server allows cors from that origin ;
        configuration.setAllowCredentials(false); // since we're using jwt no cookies
        configuration.setAllowedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider clientAuthenticationProvider() { // tell spring to use this to auth the users
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(ClientDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());// using the same encoder
        return authenticationProvider;
    }
    @Bean
    public AuthenticationProvider AdminAuthenticationProvider() { // tell spring to use this to auth the users
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(AdminDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());// using the same encoder
        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(clientAuthenticationProvider())
                .authenticationProvider(AdminAuthenticationProvider())
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService ClientDetailsService(){
        return clientDetailsService;
    }
    @Bean
    public UserDetailsService AdminDetailsService(){ return  adminDetailsService; }
}
