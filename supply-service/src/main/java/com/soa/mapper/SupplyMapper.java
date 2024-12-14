package com.soa.mapper;

import com.soa.dto.SupplyDto;
import com.soa.dto.SupplyDtoPost;
import com.soa.entity.Supply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Stanislav Hlova
 */
@Mapper(componentModel = "spring",
        uses = {UtilMapper.class, ShoePairInfoMapper.class, ShoePairInfoSupplyMapper.class})
public interface SupplyMapper {
    @Mapping(source = "id",target = "id",qualifiedByName = "fromObjectIdToStringId")
    SupplyDto toDto (Supply supply);


    Supply toEntity(SupplyDtoPost supplyDtoPost);
}
