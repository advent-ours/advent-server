package com.adventours.calendar.exception;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super(ResCode.CAL100);
    }
}
