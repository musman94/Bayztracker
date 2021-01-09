package com.bayzat.bayztracker.exception;

import com.bayzat.bayztracker.constant.ExceptionMessageConstants;

public class UnexpectedEventException extends RuntimeException {

    public UnexpectedEventException() {
        super(ExceptionMessageConstants.UNEXPECTED_EVENT_MESSAGE);
    }

    public UnexpectedEventException(Throwable cause) {
        super(ExceptionMessageConstants.UNEXPECTED_EVENT_MESSAGE, cause);
    }

    public UnexpectedEventException(String message) {
        super(message);
    }
}
