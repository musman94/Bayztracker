package com.bayzat.bayztracker.exception;

import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.NOT_FOUND_MESSAGE;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super(NOT_FOUND_MESSAGE);
    }

    public NotFoundException(Throwable cause) {
        super(NOT_FOUND_MESSAGE, cause);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
