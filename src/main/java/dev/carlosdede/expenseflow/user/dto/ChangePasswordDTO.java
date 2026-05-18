package dev.carlosdede.expenseflow.user.dto;

public record ChangePasswordDTO(
        String currentPassword,
        String newPassword
) {}
