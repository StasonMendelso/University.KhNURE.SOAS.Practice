package com.soa.registry.repository;

import com.soa.registry.entity.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Stanislav Hlova
 */
@Repository
public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, Long> {
    List<ServiceInfo> findServiceInfoByName(String name);
    Optional<ServiceInfo> findServiceInfoByNameAndVersion(String name, String version);
}
