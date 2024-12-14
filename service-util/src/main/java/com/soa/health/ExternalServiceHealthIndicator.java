package com.soa.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * @author Stanislav Hlova
 */
public class ExternalServiceHealthIndicator implements HealthIndicator {
    private final String serviceName;

    public ExternalServiceHealthIndicator(String serviceName) {
        this.serviceName = serviceName;
    }


    @Override
    public Health health() {
        return Health.up()
                .withDetail("Service", serviceName)
                .build();
    }
}
