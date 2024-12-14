package com.soa.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
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

    public ShutdownUnregisterServiceListener(String serviceName, String host, int port) {
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOG.info("Unregistering service '{}' from registry {}:{}", serviceName, host, port);

        Map<String, String> payload = new HashMap<>();
        payload.put("serviceName", serviceName);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + host + ":" + port + "/unregister";

        try {
            String response = restTemplate.postForObject(url, payload, String.class);
            LOG.info("Unregistered service successfully! Response: {}", response);
        } catch (Exception e) {
            LOG.error("Failed to unregister service: {}", e.getMessage(), e);
        }
    }
}
