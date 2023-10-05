package com.adventours.calendar.exception;

import com.adventours.calendar.global.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected CommonResponse<Void> handleCustomException(BaseException e, HttpServletRequest request) {
        log.info("[예외 발생] request url: {}", request.getRequestURI(), e);
        return new CommonResponse<>(e.getResCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonResponse<Void>> handleInternalErrorException(Exception e, HttpServletRequest request) {
        log.error("[Internal Error Message] request url: {}", request.getRequestURI(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CommonResponse<>(ResCode.HBD500));
    }

    @ExceptionHandler({
            ServletRequestBindingException.class,
            MethodArgumentTypeMismatchException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MultipartException.class,
            MissingServletRequestParameterException.class,
            BindException.class
    })
    protected ResponseEntity<CommonResponse<Void>> handle400Exception(Exception e, HttpServletRequest request) {
        log.info("[잘못된 요청] request url: {}", request.getRequestURI(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CommonResponse<>(ResCode.HBD100));
    }
}

