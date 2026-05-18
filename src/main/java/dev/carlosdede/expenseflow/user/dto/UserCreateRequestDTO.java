package dev.carlosdede.expenseflow.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




public record UserCreateRequestDTO(
         @NotBlank(message = "O nome é obrigatório")
         String name,

         @NotBlank(message = "E-mail obrigatório")
         @Email(message = "E-mail inválido")
         String email,

         @NotBlank(message = "A senha é obrigatória")
         @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres")
         String password,

         String phone,
         String document,
         AddressRequestDTO addressRequestDTO

){}
