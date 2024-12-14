package com.soa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoePairInfo {
    private ObjectId id;
    private ObjectId originalShoePairInfoId;
    private String color;
    private Double size;
    private String article;
    private Integer count;
    private List<String> codeList;
    private BigDecimal price;
}
