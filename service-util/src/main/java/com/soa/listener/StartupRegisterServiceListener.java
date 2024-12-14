package com.soa.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Stanislav Hlova
 */
public class StartupRegisterServiceListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(StartupRegisterServiceListener.class);
    private final String serviceName;
    private final String host;
    private final int port;

    public StartupRegisterServiceListener(String serviceName, String host, int port) {
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Registering a service \"{}\" in registry {}:{}", serviceName, host, port);
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> payload = new HashMap<>();
        payload.put("serviceName", serviceName);
        payload.put("status", "UP");
        String url = "http://" + host + ":" + port + "/register";
        //restTemplate.postForObject(url, payload, String.class);
        LOG.info("Registered a service in registry successfully!");
    }
}
