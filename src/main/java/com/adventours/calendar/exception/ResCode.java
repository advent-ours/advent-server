package com.adventours.calendar.exception;

import org.springframework.http.HttpStatus;

public enum ResCode {
    CAL100(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ResCode(final HttpStatus unauthorized, final String message) {
        this.httpStatus = unauthorized;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
