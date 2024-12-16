package com.soa.registry.schedule;

import com.soa.registry.entity.ServiceInfo;
import com.soa.registry.entity.ServiceStatus;
import com.soa.registry.repository.ServiceInfoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
@Component
@RequiredArgsConstructor
public class HealthCheckRegisteredServicesScheduler {
    private static final Logger logger = LoggerFactory.getLogger(HealthCheckRegisteredServicesScheduler.class);
    private static final Logger dbLogger = LoggerFactory.getLogger("DB_LOGGER");
    private final ServiceInfoRepository serviceInfoRepository;
    private final RestTemplate restTemplate;

    @Scheduled(fixedRate = 60_000) // Every 1 minute
    public void checkServiceHealth() {
        logger.info("Starting service health check...");

        List<ServiceInfo> services = serviceInfoRepository.findAll();

        for (ServiceInfo service : services) {
            if (service.getEndTime() != null && LocalDateTime.now().isAfter(service.getEndTime())) {
                continue;
            }
            String url = buildServiceUrl(service);

            try {
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    service.setStatus(ServiceStatus.UP);
                    logger.info("Service {} is UP", service.getName());
                    MDC.put("service_id", String.valueOf(service.getId()));
                    dbLogger.info("Service {} is UP", service.getName());
                    MDC.clear();
                } else {
                    if (service.getStatus() == ServiceStatus.DOWN){
                        continue;
                    }
                    service.setStatus(ServiceStatus.DOWN);
                    logger.warn("Service {} responded with status: {}", service.getName(), response.getStatusCode());
                    MDC.put("service_id", String.valueOf(service.getId()));
                    dbLogger.warn("Service {} responded with status: {}. Set Status to DOWN", service.getName(), response.getStatusCode());
                    MDC.clear();
                }
            } catch (Exception exception) {
                if (service.getStatus() == ServiceStatus.DOWN){
                    continue;
                }
                service.setStatus(ServiceStatus.DOWN);
                logger.error("Service {} is DOWN. Error: {}", service.getName(), exception.getMessage());
                MDC.put("service_id", String.valueOf(service.getId()));
                dbLogger.error("Service {} is DOWN. Error: {}", service.getName(),  exception.getMessage(), exception);
                MDC.clear();
            }

            serviceInfoRepository.save(service);
        }

        logger.info("Service health check completed.");
    }

    private String buildServiceUrl(ServiceInfo service) {
        String protocol = service.getProtocol() != null ? service.getProtocol() : "http";
        String applicationPath = service.getApplicationPath() != null ? service.getApplicationPath() : "";
        String healthCheckPath = service.getHealthCheckPath();
        return String.format("%s://%s:%d%s%s", protocol, service.getHost(), service.getPort(), applicationPath, healthCheckPath);
    }

}
