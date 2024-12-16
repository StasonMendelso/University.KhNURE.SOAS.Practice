package com.soa.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Stanislav Hlova
 */
public class StartupRegisterServiceListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(StartupRegisterServiceListener.class);
    private final String serviceName;
    private final String protocol;
    private final String host;
    private final int port;
    private final String applicationPath;
    private final String healthCheckPath;
    private final String version;
    private final String registryHost;
    private final int registryPort;

    public StartupRegisterServiceListener(String serviceName, String protocol, String host, int port, String applicationPath, String healthCheckPath, String version, String registryHost, int registryPort) {
        this.serviceName = serviceName;
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.applicationPath = applicationPath;
        this.healthCheckPath = healthCheckPath;
        this.version = version;
        this.registryHost = registryHost;
        this.registryPort = registryPort;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Registering a service \"{}\" in registry {}:{}", serviceName, host, port);
        RestTemplate restTemplate = new RestTemplate();

        // Формуємо тіло запиту
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", serviceName);
        payload.put("protocol", protocol);
        payload.put("host", host);
        payload.put("port", port);
        payload.put("applicationPath", applicationPath);
        payload.put("healthCheckPath", healthCheckPath);
        payload.put("version", version);

        // Формуємо заголовки
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Створюємо запит
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        String url = "http://" + registryHost + ":" + registryPort + "/api/v1//registry";

        try {
            // Надсилаємо POST-запит
            restTemplate.postForObject(url, request, String.class);
            LOG.info("Registered service \"{}\" in registry successfully!", serviceName);
        } catch (Exception e) {
            LOG.error("Failed to register service \"{}\" in registry. Error: {}", serviceName, e.getMessage());
        }
    }
}