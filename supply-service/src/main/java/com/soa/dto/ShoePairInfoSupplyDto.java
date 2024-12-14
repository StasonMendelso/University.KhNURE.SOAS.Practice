package com.soa.dto;

import com.soa.dto.shoe.ShoeDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
public class ShoePairInfoSupplyDto {
    private String id;
    private String siteUrl;
    private String siteName;
    private ShoeDto shoe;
    private List<ShoePairInfoDto> shoePairInfoList;
}
