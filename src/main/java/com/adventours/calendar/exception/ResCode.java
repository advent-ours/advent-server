package com.adventours.calendar.exception;

import org.springframework.http.HttpStatus;

public enum ResCode {

    //200: 정상
    CAL200("정상 처리.", HttpStatus.OK),
    //300~399: 인증 예외
    CAL300("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    CAL301("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    //400~499: 유저 예외
    CAL400("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST),
    CAL500("서버 내부 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    //600~699: 캘린더 예외
    Cal600("존재하지 않는 캘린더입니다.", HttpStatus.NOT_FOUND),
    Cal601("이미 존재하는 캘린더입니다.", HttpStatus.BAD_REQUEST);
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
