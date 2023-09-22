package com.adventours.calendar.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.adventours.calendar")
public class OpenFeignConfig {
}
