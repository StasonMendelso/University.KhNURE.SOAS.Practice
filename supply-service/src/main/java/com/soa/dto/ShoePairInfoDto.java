package com.soa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
public class ShoePairInfoDto {
    private String id;
    private String originalShoePairInfoId;
    private String color;
    private Double size;
    private String article;
    private Integer count;
    private List<String> codeList;
    private BigDecimal price;
}
