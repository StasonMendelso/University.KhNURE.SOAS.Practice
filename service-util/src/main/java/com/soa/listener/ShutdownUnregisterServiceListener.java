package com.soa.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav Hlova
 */
public class ShutdownUnregisterServiceListener implements ApplicationListener<ContextClosedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ShutdownUnregisterServiceListener.class);
    private final String serviceName;
    private final String host;
    private final int port;
    private final String version;

    public ShutdownUnregisterServiceListener(String serviceName, String host, int port, String version) {
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
        this.version = version;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOG.info("Unregistering service '{}' from registry {}:{}", serviceName, host, port);

        Map<String, String> payload = new HashMap<>();
        payload.put("name", serviceName);
        payload.put("version", version);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + host + ":" + port + "/api/v1/registry";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            restTemplate.put(url, request);
            LOG.info("Unregistered service '{}' successfully!", serviceName);
        } catch (Exception e) {
            LOG.error("Failed to unregister service '{}': {}", serviceName, e.getMessage(), e);
        }
    }
}
