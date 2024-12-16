package com.soa.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRegisterInfoDto {
    private String name;
    private String protocol;
    private String host;
    private int port;
    private String applicationPath;
    private String healthCheckPath;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String version;
}
