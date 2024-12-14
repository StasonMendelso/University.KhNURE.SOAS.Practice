package com.soa.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Named;

/**
 * @author Stanislav Hlova
 */
public class UtilMapper {
    @Named("fromObjectIdToStringId")
    static String fromObjectIdToStringId(ObjectId objectId){
        return objectId != null ? objectId.toHexString() : null;
    }
}
