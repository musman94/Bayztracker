package com.bayzat.bayztracker.exception;

import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.NOT_AUTHORIZED_MESSAGE;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException() {
        super(NOT_AUTHORIZED_MESSAGE);
    }

    public NotAuthorizedException(Throwable cause) {
        super(NOT_AUTHORIZED_MESSAGE, cause);
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
