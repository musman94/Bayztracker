package com.bayzat.bayztracker.exception;

import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.UNEXPECTED_EVENT_MESSAGE;

public class UnexpectedEventException extends RuntimeException {

    public UnexpectedEventException() {
        super(UNEXPECTED_EVENT_MESSAGE);
    }

    public UnexpectedEventException(Throwable cause) {
        super(UNEXPECTED_EVENT_MESSAGE, cause);
    }

    public UnexpectedEventException(String message) {
        super(message);
    }
}
