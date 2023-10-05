package com.adventours.calendar.exception;

public class NotOwnerException extends BaseException {
    public NotOwnerException() {
        super(ResCode.HBD701);
    }
}
