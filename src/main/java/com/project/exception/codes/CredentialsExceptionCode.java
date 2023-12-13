package com.project.exception.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CredentialsExceptionCode {

    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    LOGIN_IN_USE("Login is already in use", HttpStatus.CONFLICT);

    private String errorMessage;
    private HttpStatus status;

}
