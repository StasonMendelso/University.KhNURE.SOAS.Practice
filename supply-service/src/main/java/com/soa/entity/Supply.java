package com.soa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Supply {
    private ObjectId id;
    private SupplyStatus supplyStatus;
    private String deliveryName;
    private String deliveryInvoiceNumber;
    private BigDecimal deliveryTotal;
    private LocalDateTime receivedAt;
    private List<ShoePairInfoSupply> shoePairInfoSupplyList;
}
