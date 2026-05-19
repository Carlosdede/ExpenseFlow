package dev.carlosdede.expenseflow.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordDTO(
        @NotBlank(message = "A senha é obrigatória")
        String currentPassword,
        @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres")
        String newPassword
) {}
