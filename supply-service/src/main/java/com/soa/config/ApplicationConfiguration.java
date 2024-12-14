package com.soa.config;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.soa.health.ExternalServiceHealthIndicator;
import com.soa.listener.ShutdownUnregisterServiceListener;
import com.soa.listener.StartupRegisterServiceListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Stanislav Hlova
 */
@Configuration
public class ApplicationConfiguration {

    @Value("${service.name}")
    String serviceName;
    @Value("${service-registry.host}")
    String registryHost;
    @Value("${service-registry.port}")
    int registryPort;
    @Value("${database.mongodb.connection-string}")
    String mongoDbConnectionString;
    @Value("${database.mongodb.database-name}")
    String mongoDbDatabaseName;

    @Bean
    public ExternalServiceHealthIndicator healthIndicator(){
        return new ExternalServiceHealthIndicator(serviceName);
    }
    @Bean
    public StartupRegisterServiceListener startupRegisterServiceListener(){
        return new StartupRegisterServiceListener(serviceName,registryHost,registryPort);
    }
    @Bean
    public ShutdownUnregisterServiceListener shutdownUnregisterServiceListener(){
        return new ShutdownUnregisterServiceListener(serviceName,registryHost,registryPort);
    }

    @Bean
    public MongoDatabase mongoDatabase(){
        return MongoClients.create(mongoDbConnectionString).getDatabase(mongoDbDatabaseName);
    }
}
