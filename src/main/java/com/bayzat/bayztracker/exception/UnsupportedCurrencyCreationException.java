package com.bayzat.bayztracker.exception;

import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.UNSUPPORTED_CURRENCY_MESSAGE;

public class UnsupportedCurrencyCreationException extends RuntimeException {
    public UnsupportedCurrencyCreationException() {
        super(UNSUPPORTED_CURRENCY_MESSAGE);
    }

    public UnsupportedCurrencyCreationException(String message) {
        super(message);
    }
}
