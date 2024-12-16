package com.soa.registry.mapper;

/**
 * @author Stanislav Hlova
 */

import com.soa.registry.dto.ServiceInfoDto;
import com.soa.registry.dto.ServiceRegisterInfoDto;
import com.soa.registry.entity.ServiceInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceInfoMapper {
    ServiceInfoDto toDto(ServiceInfo entity);
    ServiceInfo toEntity(ServiceRegisterInfoDto dto);
}
