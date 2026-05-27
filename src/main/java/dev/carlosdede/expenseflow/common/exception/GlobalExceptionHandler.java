package dev.carlosdede.expenseflow.common.exception;


import dev.carlosdede.expenseflow.common.exception.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                exception.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );
        return ResponseEntity.status(status).body(errorResponseDTO);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception) {
        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                exception.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );

        return  ResponseEntity.status(status).body(errorResponseDTO);
    }
}
