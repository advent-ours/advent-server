package com.adventours.calendar.user.service;

import com.adventours.calendar.user.domain.OAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class OAuthPortMapper {
    @Bean
    public Map<OAuthProvider, OAuthRequestPort> oAuthServiceEnumMap(List<OAuthRequestPort> oAuthRequestPorts) {
        return new EnumMap<>(oAuthRequestPorts
                .stream()
                .collect(Collectors.toMap(OAuthRequestPort::getOAuthProvider, u -> u)));
    }
}
