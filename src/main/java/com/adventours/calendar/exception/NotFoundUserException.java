package com.adventours.calendar.exception;

public class NotFoundUserException extends BaseException {
    public NotFoundUserException() {
        super(ResCode.CAL400);
    }
}
