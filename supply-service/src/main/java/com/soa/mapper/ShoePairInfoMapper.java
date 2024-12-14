package com.soa.mapper;

import com.soa.dto.ShoePairInfoDto;
import com.soa.dto.ShoePairInfoDtoPost;
import com.soa.entity.ShoePairInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Stanislav Hlova
 */
@Mapper(componentModel = "spring", uses = {UtilMapper.class})
public interface ShoePairInfoMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "fromObjectIdToStringId")
    @Mapping(source = "originalShoePairInfoId", target = "originalShoePairInfoId", qualifiedByName = "fromObjectIdToStringId")
    ShoePairInfoDto toDto(ShoePairInfo shoePairInfo);

    ShoePairInfo toEntity(ShoePairInfoDtoPost shoePairInfoDtoPost);
}
