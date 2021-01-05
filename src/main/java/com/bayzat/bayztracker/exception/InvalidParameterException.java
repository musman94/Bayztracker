package com.bayzat.bayztracker.exception;

import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.INVALID_PARAMETER_MESSAGE;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException() {
        super(INVALID_PARAMETER_MESSAGE);
    }

    public InvalidParameterException(Throwable cause) {
        super(INVALID_PARAMETER_MESSAGE, cause);
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}