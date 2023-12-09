package com.project.exception.entity;

import com.project.exception.codes.TokenExceptionCode;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException{

    private TokenExceptionCode code;

    public TokenException(TokenExceptionCode code) {
        super(code.getErrorMessage());
        this.code = code;
    }
}
