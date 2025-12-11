package com.Project1.Car.Insurance.System.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CompleteProfileDto(
        @NotNull
        @Size(min = 3)
        String fName,

        @Size(min = 3)
        @NotNull
        String lName,

        @Size(min = 5)
        @NotNull
        String address,

        @NotNull
        @Size(min = 13,max = 13)
        //@Pattern()
        String nationalId,

        @NotNull
        @Size(min = 10,max = 10)
        //@Pattern()
        String phoneNumber
) {
}
