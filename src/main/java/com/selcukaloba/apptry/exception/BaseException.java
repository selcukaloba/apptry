package com.selcukaloba.apptry.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public BaseException(ErrorMessage errorMessage)
    {
        super(errorMessage.generateErrorMessage());
        this.errorMessage=errorMessage;
    }
}
