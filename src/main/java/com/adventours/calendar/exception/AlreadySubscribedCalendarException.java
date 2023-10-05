package com.adventours.calendar.exception;

public class AlreadySubscribedCalendarException extends BaseException {
    public AlreadySubscribedCalendarException() {
        super(ResCode.HBD890);
    }
}
