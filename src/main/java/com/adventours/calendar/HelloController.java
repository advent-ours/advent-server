package com.adventours.calendar;

import com.adventours.calendar.global.CommonResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "진우 지호 찬희 고운 화이팅 ";
    }

    @GetMapping("/exception")
    public ResponseEntity<CommonResponse<Void>> testExceptionGet(
            @RequestParam final String query) {
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @PostMapping("/exception/{path}")
    public ResponseEntity<CommonResponse<Void>> testExceptionPost(
            @PathVariable final Long path,
            @Valid @RequestBody final ExceptionTestBody body) {
        return ResponseEntity.ok(new CommonResponse<>());
    }

    public record ExceptionTestBody(
            @NotNull
            Integer integerData) {
    }
}
