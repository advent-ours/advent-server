package com.adventours.calendar.exception;

import com.adventours.calendar.global.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected CommonResponse<Void> handleCustomException(BaseException e, HttpServletRequest request) {
        log.warn("[예외 발생] request url: {}", request.getRequestURI(), e);
        return new CommonResponse<>(e.getResCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonResponse<Void>> handleInternalErrorException(Exception e, HttpServletRequest request) {
        log.error("[Internal Error Message] request url: {}", request.getRequestURI(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CommonResponse<>(ResCode.CAL500));
    }

}

