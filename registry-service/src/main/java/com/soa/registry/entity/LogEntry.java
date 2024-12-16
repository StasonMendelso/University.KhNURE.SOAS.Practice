package com.soa.registry.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Stanislav Hlova
 */
@Entity
@Table(name = "logs")
@Getter
@Setter
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime timestamp;

    @Column(length = 10, nullable = false)
    private String level;

    @Column(length = 255, nullable = false)
    private String logger;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(columnDefinition = "TEXT")
    private String stacktrace;

    private Long serviceId;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
