package com.Project1.Car.Insurance.System.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RegisterDto(
        @NotNull(message = "email must not be null")
        @Email(message = "enter valid email")
        String email,

        @NotNull(message = "email must not be null")
        @Pattern(regexp = "[A-Za-z\\d]{8,20}$" ,message = "password must be 8-20 char, only letters or numbers")

        String password
) {
}
