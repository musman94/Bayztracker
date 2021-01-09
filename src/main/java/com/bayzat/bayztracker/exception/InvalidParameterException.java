package com.bayzat.bayztracker.exception;

import com.bayzat.bayztracker.constant.ExceptionMessageConstants;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException() {
        super(ExceptionMessageConstants.INVALID_PARAMETER_MESSAGE);
    }

    public InvalidParameterException(Throwable cause) {
        super(ExceptionMessageConstants.INVALID_PARAMETER_MESSAGE, cause);
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}