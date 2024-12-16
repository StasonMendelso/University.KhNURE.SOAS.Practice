package com.soa.registry.repository;

import com.soa.registry.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stanislav Hlova
 */
@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Integer> {
}
