package com.soa.mapper;

import com.soa.dto.ShoePairInfoSupplyDto;
import com.soa.dto.ShoePairInfoSupplyDtoPost;
import com.soa.entity.ShoePairInfoSupply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Stanislav Hlova
 */
@Mapper(componentModel = "spring",
        uses = {UtilMapper.class, ShoePairInfoMapper.class, ShoeMapper.class})
public interface ShoePairInfoSupplyMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "fromObjectIdToStringId")
    @Mapping(source = "shoe", target = "shoe", qualifiedByName = "toDtoDynamic")
    ShoePairInfoSupplyDto toDto(ShoePairInfoSupply shoePairInfoSupply);

    @Mapping(source = "shoe", target = "shoe", qualifiedByName = "toEntityDynamic")
    ShoePairInfoSupply toEntity(ShoePairInfoSupplyDtoPost shoePairInfoSupplyDtoPost);
}
