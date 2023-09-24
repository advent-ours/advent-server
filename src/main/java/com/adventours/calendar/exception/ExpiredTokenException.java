package com.adventours.calendar.exception;

public class ExpiredTokenException extends BaseException {

    public ExpiredTokenException() {
        super(ResCode.CAL100);
    }
}
