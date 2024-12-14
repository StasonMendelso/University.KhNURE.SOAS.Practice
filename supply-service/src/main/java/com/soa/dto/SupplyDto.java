package com.soa.dto;

import com.soa.entity.SupplyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
public class SupplyDto {
    private String id;
    private SupplyStatus supplyStatus;
    private String deliveryName;
    private String deliveryInvoiceNumber;
    private BigDecimal deliveryTotal;
    private LocalDateTime receivedAt;
    private List<ShoePairInfoSupplyDto> shoePairInfoSupplyList;
}
