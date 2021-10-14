package com.jinho.homepage.exception;

import com.jinho.homepage.util.ValidationConstant;

public class BadRequestException extends Exception {

    private ValidationConstant errorCode;


    public BadRequestException(ValidationConstant tokenNotFound) {
        this.errorCode = ValidationConstant.TOKEN_NOT_FOUND;
    }
}
