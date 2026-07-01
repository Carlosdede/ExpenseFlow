package dev.carlosdede.expenseflow.common.exception.dto;

public record ErrorResponseDTO(
        String message,
        int status,
        String error
) {
}
