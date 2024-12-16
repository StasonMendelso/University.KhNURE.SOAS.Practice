package com.soa.registry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

/**
 * @author Stanislav Hlova
 */
@Entity
@Table(name = "service_info")
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServiceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 16)
    private String protocol;
    private String host;
    private int port;
    @Column(length = 200)
    private String applicationPath;
    private String healthCheckPath;
    @Enumerated(EnumType.STRING)
    private ServiceStatus status;
    @Column(length = 20)
    private String version;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
