package dev.carlosdede.expenseflow.user.dto;

import dev.carlosdede.expenseflow.address.dto.AddressRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




public record UserCreateRequestDTO(
         @NotBlank(message = "O nome é obrigatório")
         @Size(max = 120)
         String name,

         @NotBlank(message = "E-mail obrigatório")
         @Email(message = "E-mail inválido")
         @Size(max = 155)
         String email,

         @NotBlank(message = "A senha é obrigatória")
         @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres")
         String password,

         @Size(max = 20)
         String phone,

         @Size(max = 20)
         String document,
         AddressRequestDTO addressRequestDTO

){}
