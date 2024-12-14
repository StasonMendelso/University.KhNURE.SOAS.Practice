package com.soa.dto;

import com.soa.dto.shoe.ShoeDtoPost;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
public class ShoePairInfoSupplyDtoPost {
    private String siteUrl;
    private String siteName;
    private ShoeDtoPost shoe;
    private List<ShoePairInfoDtoPost> shoePairInfoList;
}
