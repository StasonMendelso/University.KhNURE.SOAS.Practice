package com.soa.mapper;

import com.soa.dto.shoe.*;
import com.soa.entity.ShoeType;
import com.soa.entity.shoe.Heels;
import com.soa.entity.shoe.Shoe;
import com.soa.entity.shoe.Slippers;
import com.soa.entity.shoe.Sneakers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author Stanislav Hlova
 */
@Mapper(componentModel = "spring",
        uses = {UtilMapper.class})
public interface ShoeMapper {

    @Named("toDtoDynamic")
    default ShoeDto toDto(Shoe shoe) {
        if (shoe instanceof Heels) {
            return toDto((Heels) shoe);
        }
        if (shoe instanceof Slippers) {
            return toDto((Slippers) shoe);
        }
        if (shoe instanceof Sneakers) {
            return toDto((Sneakers) shoe);
        }
        return mapShoeToDto(shoe);
    }

    @Named("toEntityDynamic")
    default Shoe toEntity(ShoeDtoPost shoeDtoPost) {
        if (shoeDtoPost.getShoeType() == ShoeType.HEELS) {
            return toEntityHeels(shoeDtoPost);
        }
        if (shoeDtoPost.getShoeType() == ShoeType.SLIPPERS) {
            return toEntitySlippers(shoeDtoPost);
        }
        if (shoeDtoPost.getShoeType() == ShoeType.SNEAKERS) {
            return toEntitySneakers(shoeDtoPost);
        }
        return toEntityShoe(shoeDtoPost);
    }

    @Mapping(source = "id", target = "id", qualifiedByName = "fromObjectIdToStringId")
    @Mapping(source = "originalShoeId", target = "originalShoeId", qualifiedByName = "fromObjectIdToStringId")
    ShoeDto mapShoeToDto(Shoe shoe);

    @Mapping(source = "id", target = "id", qualifiedByName = "fromObjectIdToStringId")
    @Mapping(source = "originalShoeId", target = "originalShoeId", qualifiedByName = "fromObjectIdToStringId")
    HeelsDto toDto(Heels heels);

    @Mapping(source = "id", target = "id", qualifiedByName = "fromObjectIdToStringId")
    @Mapping(source = "originalShoeId", target = "originalShoeId", qualifiedByName = "fromObjectIdToStringId")
    SneakersDto toDto(Sneakers sneakers);

    @Mapping(source = "id", target = "id", qualifiedByName = "fromObjectIdToStringId")
    @Mapping(source = "originalShoeId", target = "originalShoeId", qualifiedByName = "fromObjectIdToStringId")
    SlippersDto toDto(Slippers slippers);


    Shoe toEntityShoe(ShoeDtoPost shoeDtoPost);

    Heels toEntityHeels(ShoeDtoPost shoeDtoPost);

    Sneakers toEntitySneakers(ShoeDtoPost shoeDtoPost);

    Slippers toEntitySlippers(ShoeDtoPost shoeDtoPost);
}
