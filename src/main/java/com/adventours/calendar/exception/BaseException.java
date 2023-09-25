package com.adventours.calendar.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ResCode resCode;
    private final String message;

    protected BaseException(ResCode resCode) {
        super();
        this.resCode = resCode;
        this.message = resCode.getMessage();
    }

    BaseException(final ResCode resCode, final String customMessage) {
        super();
        this.resCode = resCode;
        this.message = customMessage;
    }

}