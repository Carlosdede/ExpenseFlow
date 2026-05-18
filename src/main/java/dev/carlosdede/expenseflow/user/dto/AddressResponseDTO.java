package dev.carlosdede.expenseflow.user.dto;

public record AddressResponseDTO(

        String zipCode,
        String street,
        String number,
        String district,
        String city,
        String state,
        String complement

) {
}
