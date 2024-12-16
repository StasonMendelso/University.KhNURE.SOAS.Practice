package com.soa.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Stanislav Hlova
 */
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class RegistryServiceRunner {

    public static void main(String[] args) {
        SpringApplication.run(RegistryServiceRunner.class, args);
    }

}
