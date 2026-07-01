package dev.carlosdede.expenseflow.address.dto;

public record AddressUpdateDTO(
        String zipCode,
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String complement
) {}
