package com.soa.registry.service;

import com.soa.registry.dto.ServiceInfoDto;
import com.soa.registry.dto.ServiceRegisterInfoDto;
import com.soa.registry.dto.ServiceUnregisterInfoDto;
import com.soa.registry.entity.ServiceInfo;
import com.soa.registry.entity.ServiceStatus;
import com.soa.registry.exception.ServiceInfoNotFoundException;
import com.soa.registry.mapper.ServiceInfoMapper;
import com.soa.registry.repository.ServiceInfoRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Stanislav Hlova
 */
@Service
@AllArgsConstructor
public class RegistryService {
    private static final Logger dbLogger = LoggerFactory.getLogger("DB_LOGGER");
    private static final Logger logger = LoggerFactory.getLogger(RegistryService.class);

    private final ServiceInfoRepository serviceInfoRepository;
    private final ServiceInfoMapper serviceInfoMapper;
    private final EntityManager entityManager;

    public List<ServiceInfoDto> findAllServiceInfo() {
        return serviceInfoRepository.findAll().stream()
                .map(serviceInfoMapper::toDto)
                .toList();
    }

    public List<ServiceInfoDto> findServiceInfoByName(String serviceName) {
        List<ServiceInfoDto> list = serviceInfoRepository.findServiceInfoByName(serviceName).stream()
                .map(serviceInfoMapper::toDto)
                .toList();
        if (list.isEmpty()) {
            throw new ServiceInfoNotFoundException(null, serviceName, null);
        }
        return list;
    }

    @Transactional
    public ServiceInfoDto registerService(ServiceRegisterInfoDto serviceRegisterInfoDto) {
        ServiceInfo serviceInfo = serviceInfoMapper.toEntity(serviceRegisterInfoDto);
        Optional<ServiceInfo> serviceFromDb = serviceInfoRepository.findServiceInfoByNameAndVersion(serviceRegisterInfoDto.getName(), serviceRegisterInfoDto.getVersion());
        if (serviceFromDb.isEmpty()) {
            serviceInfo.setStatus(ServiceStatus.UP);
            ServiceInfo saved = serviceInfoRepository.save(serviceInfo);
            entityManager.refresh(saved);
            logger.info("New service {} was registered with status {}", saved.getName(), saved.getStatus());
            MDC.put("service_id", String.valueOf(saved.getId()));
            dbLogger.info("New service {} was registered with status {}", saved.getName(), saved.getStatus());
            MDC.clear();
            return serviceInfoMapper.toDto(saved);
        }
        ServiceInfo info = serviceFromDb.get();
        info.setStatus(ServiceStatus.UP);
        info.setPort(serviceInfo.getPort());
        info.setHost(serviceInfo.getHost());
        info.setEndTime(serviceInfo.getEndTime());
        info.setProtocol(serviceInfo.getProtocol());
        info.setApplicationPath(serviceInfo.getApplicationPath());
        info.setHealthCheckPath(serviceInfo.getHealthCheckPath());
        info.setVersion(serviceInfo.getVersion());
        ServiceInfo saved = serviceInfoRepository.saveAndFlush(info);
        entityManager.refresh(saved);
        logger.info("Service {} was re-registered with status {}", saved.getName(), saved.getStatus());
        MDC.put("service_id", String.valueOf(saved.getId()));
        dbLogger.info("Service {} was re-registered with status {}", saved.getName(), saved.getStatus());
        MDC.clear();
        return serviceInfoMapper.toDto(saved);
    }

    @Transactional
    public ServiceInfoDto unregisterService(ServiceUnregisterInfoDto serviceUnregisterInfoDto) {
        Optional<ServiceInfo> serviceFromDb = serviceInfoRepository.findServiceInfoByNameAndVersion(serviceUnregisterInfoDto.getName(), serviceUnregisterInfoDto.getVersion());
        if (serviceFromDb.isEmpty()) {
            throw new ServiceInfoNotFoundException(null, serviceUnregisterInfoDto.getName(), serviceUnregisterInfoDto.getVersion());
        }
        ServiceInfo info = serviceFromDb.get();
        info.setStatus(ServiceStatus.DOWN);
        ServiceInfo saved = serviceInfoRepository.saveAndFlush(info);
        entityManager.refresh(saved);
        logger.info("Service {} was deregistered. Status was set to {}", saved.getName(), saved.getStatus());
        MDC.put("service_id", String.valueOf(saved.getId()));
        dbLogger.info("Service {} was deregistered. Status was set to {}", saved.getName(), saved.getStatus());
        MDC.clear();
        return serviceInfoMapper.toDto(saved);
    }
}
