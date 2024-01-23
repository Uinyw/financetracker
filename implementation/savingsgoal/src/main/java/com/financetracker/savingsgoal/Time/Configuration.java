package com.financetracker.savingsgoal.Time;

import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.ZoneId;

@org.springframework.context.annotation.Configuration
public class Configuration {

    public static final String TIME_ZONE = "Europe/Berlin";

    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of(TIME_ZONE));
    }
}
