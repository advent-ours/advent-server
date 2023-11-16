package com.adventours.calendar.exception;

public class NotSupportedDeviceException extends BaseException {

    public NotSupportedDeviceException() {
        super(ResCode.HBD100);
    }
}
