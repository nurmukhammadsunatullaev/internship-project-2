package com.project.exception.entity;

import com.project.exception.codes.CredentialsExceptionCode;
import lombok.Getter;

@Getter
public class CredentialsException extends RuntimeException{

    private CredentialsExceptionCode code;

    public CredentialsException(CredentialsExceptionCode code) {
        super(code.getErrorMessage());
        this.code = code;
    }
}
