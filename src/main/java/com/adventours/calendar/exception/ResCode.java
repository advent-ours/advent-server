package com.adventours.calendar.exception;

import org.springframework.http.HttpStatus;

public enum ResCode {

    // 100: 클라이언트 예외
    HBD100("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),


    // 200: 정상
    HBD200("정상 처리.", HttpStatus.OK),


    // 500: 서버 내부 에러
    HBD500("서버 내부 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),


    // 600~699: 인증 예외
    HBD600("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    HBD601("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),


    // 700~799: 유저 관련 예외
    HBD700("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST),
    HBD701("해당 컨텐츠의 주인이 아닙니다.", HttpStatus.BAD_REQUEST),


    // 800~899: 캘린더 관련 예외
    HBD800("존재하지 않는 캘린더입니다.", HttpStatus.NOT_FOUND),
    HBD801("이미 존재하는 캘린더입니다.", HttpStatus.BAD_REQUEST),
    HBD850("존재하지 않는 선물입니다.", HttpStatus.NOT_FOUND),
    HBD890("이미 구독한 캘린더입니다.", HttpStatus.BAD_REQUEST);

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
