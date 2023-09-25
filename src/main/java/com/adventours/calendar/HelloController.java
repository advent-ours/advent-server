package com.adventours.calendar;

import com.adventours.calendar.auth.Auth;
import com.adventours.calendar.auth.UserContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Auth
    @GetMapping("/")
    public String hello() {
        final Long test = UserContext.CONTEXT.get();
        return "진우 지호 찬희 고운 화이팅 " + test;
    }
}
