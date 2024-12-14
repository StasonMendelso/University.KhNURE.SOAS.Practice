package com.soa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
public class ShoePairInfoDtoPost {
    private String color;
    private Double size;
    private String article;
    private Integer count;
    private BigDecimal price;
}
