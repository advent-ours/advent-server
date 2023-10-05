package com.adventours.calendar.exception;

public class NotFoundGiftException extends BaseException {
    public NotFoundGiftException() {
        super(ResCode.HBD850);
    }
}
