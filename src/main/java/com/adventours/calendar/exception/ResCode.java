package com.adventours.calendar.exception;

import org.springframework.http.HttpStatus;

public enum ResCode {

    //200: 정상
    //300~399: 인증 예외
    CAL300("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    CAL301("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    //400~499: 유저 예외
    CAL400("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
    //500~599: 캘린더 예외
    private final String message;
    private final HttpStatus httpStatus;

    ResCode(final String message, final HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
