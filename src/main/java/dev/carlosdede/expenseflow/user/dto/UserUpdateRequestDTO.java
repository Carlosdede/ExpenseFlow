package dev.carlosdede.expenseflow.user.dto;


import dev.carlosdede.expenseflow.address.dto.AddressRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDTO (

         String name,

         @Email(message = "E-mail não é valido")
         String email,

         @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres")
         String phone
){}

