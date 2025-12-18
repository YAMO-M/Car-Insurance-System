package com.Project1.Car.Insurance.System.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthRequest(
        @NotNull
        @Email(message = "Invalid Email")
        String email,

        @NotNull
        @Pattern(regexp = "[A-Za-z\\d]{8,20}$" ,message = "password must be 8-20 char, only letters and numbers")
        String password
) {
}
