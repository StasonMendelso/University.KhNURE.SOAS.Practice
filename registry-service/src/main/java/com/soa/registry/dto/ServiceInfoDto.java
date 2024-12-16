package com.soa.registry.dto;

import com.soa.registry.entity.ServiceStatus;
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
public class ServiceInfoDto {
    private Long id;
    private String name;
    private String protocol;
    private String host;
    private int port;
    private String applicationPath;
    private String healthCheckPath;
    private String version;
    private ServiceStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
