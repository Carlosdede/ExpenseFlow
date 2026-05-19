package dev.carlosdede.expenseflow.address.dto;

import java.time.LocalDateTime;

public record AddressResponseDTO(

        String zipCode,
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String complement,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
