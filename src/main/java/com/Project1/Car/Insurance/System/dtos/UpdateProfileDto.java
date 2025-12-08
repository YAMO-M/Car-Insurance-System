package com.Project1.Car.Insurance.System.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProfileDto(
        @Size(min = 5)
        @NotNull
        String address,

        @NotNull
        @Size(min = 10,max = 10)
        //@Pattern()
        String phoneNumber
) {
}
