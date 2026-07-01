package dev.carlosdede.expenseflow.address.dto;

import jakarta.validation.constraints.Size;

public record AddressUpdateDTO(
        @Size(max = 10)
        String zipCode,

        @Size(max = 150)
        String street,

        @Size(max = 20)
        String number,

        @Size(max = 100)
        String neighborhood,

        @Size(max = 100)
        String city,

        @Size(max = 2)
        String state,

        @Size(max = 255)
        String complement

) {}
