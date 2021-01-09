package com.bayzat.bayztracker.exception;

import com.bayzat.bayztracker.constant.ExceptionMessageConstants;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException() {
        super(ExceptionMessageConstants.NOT_AUTHORIZED_MESSAGE);
    }

    public NotAuthorizedException(Throwable cause) {
        super(ExceptionMessageConstants.NOT_AUTHORIZED_MESSAGE, cause);
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
