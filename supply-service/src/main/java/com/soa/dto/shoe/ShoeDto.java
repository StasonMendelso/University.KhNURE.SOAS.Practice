package com.soa.dto.shoe;

import com.soa.entity.ShoeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
public class ShoeDto {
    private String id;
    private String originalShoeId;
    private String name;
    private String model;
    private String description;
    private LocalDateTime createdAt;
    private String manufacturerName;
    private ShoeType shoeType;
}
