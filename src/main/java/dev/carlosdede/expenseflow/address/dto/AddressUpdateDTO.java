package dev.carlosdede.expenseflow.address.dto;

public record AddressUpdateDTO(
        String zipCode,
        String street,
        String number,
        String district,
        String city,
        String state,
        String complement
) {}
