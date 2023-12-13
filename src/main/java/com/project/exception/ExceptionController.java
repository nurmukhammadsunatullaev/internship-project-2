package com.project.exception;

import com.project.exception.entity.CredentialsException;
import com.project.exception.entity.TokenException;
import com.project.persistence.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CredentialsException.class)
    public ResponseEntity<ErrorResponse> credentialsExceptionHandler(CredentialsException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(exception.getCode().getErrorMessage())
                .build(),
                exception.getCode().getStatus());
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> tokenExceptionHandler(TokenException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(exception.getCode().getErrorMessage())
                .build(),
                exception.getCode().getStatus());
    }
}
