package io.opentelemetry.example.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;

public class PrometheusConfig {
    // io.micrometer.core.aop.TimedAspect
// org.springframework.context.annotation.Bean
// io.micrometer.core.instrument.MeterRegistry

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
