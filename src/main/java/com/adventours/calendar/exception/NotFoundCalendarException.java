package com.adventours.calendar.exception;

public class NotFoundCalendarException extends BaseException {
    public NotFoundCalendarException() {
        super(ResCode.HBD800);
    }
}
