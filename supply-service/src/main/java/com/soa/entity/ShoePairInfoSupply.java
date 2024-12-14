package com.soa.entity;

import com.soa.entity.shoe.Shoe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoePairInfoSupply {
    private ObjectId id;
    private String siteUrl;
    private String siteName;
    private Shoe shoe;
    private List<ShoePairInfo> shoePairInfoList;
}
