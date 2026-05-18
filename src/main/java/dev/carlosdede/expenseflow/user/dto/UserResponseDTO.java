package dev.carlosdede.expenseflow.user.dto;



import java.time.LocalDateTime;
import java.util.UUID;


public record UserResponseDTO (
         UUID id,
         boolean active,
         String name,
         String email,
         String phone,
         String document,
         AddressResponseDTO address,
         LocalDateTime createdAt,
         LocalDateTime updatedAt
){}
