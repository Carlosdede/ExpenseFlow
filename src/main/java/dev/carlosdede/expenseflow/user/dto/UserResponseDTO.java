package dev.carlosdede.expenseflow.user.dto;


import dev.carlosdede.expenseflow.address.dto.AddressResponseDTO;

import java.time.LocalDateTime;
import java.util.UUID;


public record UserResponseDTO (
         UUID id,
         boolean active,
         String name,
         String email,
         String phone,
         String document,
         AddressResponseDTO addressResponseDTO,
         LocalDateTime createdAt,
         LocalDateTime updatedAt
){}
