package com.project.exception.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenExceptionCode {

    ACCESS_TOKEN_EXPIRED("Access token is expired or incorrect.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED("Refresh token is expired or incorrect.", HttpStatus.BAD_REQUEST),
    NEED_SIGN_IN("Refresh token is expired. You need to sign in to continue.", HttpStatus.CONFLICT);

    private String errorMessage;
    private HttpStatus status;
}