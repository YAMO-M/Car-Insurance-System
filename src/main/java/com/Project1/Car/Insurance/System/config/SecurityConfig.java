package com.Project1.Car.Insurance.System.config;

import com.Project1.Car.Insurance.System.security.CompositeUserDetailsService;
import com.Project1.Car.Insurance.System.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import  org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration  // allow security setup to work
@RequiredArgsConstructor
@EnableMethodSecurity // allows @PreAuthorise ..etc
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CompositeUserDetailsService compositeUserDetailsService;

    // my security rules, every http request pass this chain of filters
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception
    {
        http
                .csrf(csrf -> csrf.disable())// cross site request forgery, we dont use it
                .cors( cors -> cors.configurationSource(corsConfigurationSource())) // controll which sites can call my api

                .formLogin(form -> form.disable())// i created my own html login page
                .httpBasic(basic -> basic.disable())//i dont use it, i use jwt

                // authorization rules
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/v1/clients/register").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                // tell spring to not create sessions
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no http sessions needed, jwt carry the auth info
                )
                .exceptionHandling(exceptions -> exceptions
                        //triggered when user is not authenticated
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Authentication required\"}");
                        })
                        // trigged when client tries to access admin roles
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(403);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Access denied\"}");
                        })
                )



                //check jwt before username and password
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);// insert my filter in spring chain of filters

        return http.build();

    }
    //order of execution: 1. cors 2. csr 3. jwt 4. username and password 5. authorization

    @Bean // prevent other websites to make requests
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration  configuration  = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080","http://localhost:63342"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS")); // browser sends options first to check id server allows cors from that origin ;
        configuration.setAllowCredentials(false); // since we're using jwt no cookies
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() { // tell spring to use this to auth the users
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(compositeUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());// using the same encoder
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }


    // manages authentication, also used in client auth controller to manually trigger auth
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{

        // then add my provider
        return new ProviderManager(authenticationProvider());


    }
    // our password hasher
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    }
