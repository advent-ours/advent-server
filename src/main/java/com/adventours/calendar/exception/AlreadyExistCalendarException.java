package com.adventours.calendar.exception;

public class AlreadyExistCalendarException extends BaseException {
    public AlreadyExistCalendarException() {
        super(ResCode.Cal601);
    }
}
