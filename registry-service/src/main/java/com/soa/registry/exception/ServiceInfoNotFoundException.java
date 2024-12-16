package com.soa.registry.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Stanislav Hlova
 */
@RequiredArgsConstructor
@Data
public class ServiceInfoNotFoundException extends RuntimeException {
    private final Long id;
    private final String name;
    private final String version;
}
