package com.bayzat.bayztracker.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessageConstants {

    public static final String NOT_AUTHORIZED_MESSAGE = "Not authorized";
    public static final String ALERT_NOT_FOUND_MESSAGE = "Alert not found";
    public static final String CURRENCY_NOT_FOUND_MESSAGE = "Currency not found";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found";
    public static final String UNSUPPORTED_CURRENCY_MESSAGE = "This currency is not supported";
    public static final String CANNOT_BE_ACKED_MESSAGE = "Only a triggered alert can be acknowledged";
    public static final String CANNOT_BE_CANCELED_MESSAGE = "Alert cannot be cancelled once it has been triggered";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "User with this email already exists";
    public static final String ALERT_ALREADY_EXISTS_MESSAGE = "Alert on this currency with this price already exists";

}
