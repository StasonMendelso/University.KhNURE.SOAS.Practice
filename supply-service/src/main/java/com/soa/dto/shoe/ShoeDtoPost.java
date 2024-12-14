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
public class ShoeDtoPost {
    private String name;
    private String model;
    private String description;
    private LocalDateTime createdAt;
    private String manufacturerName;
    private ShoeType shoeType;
    private String soleMaterial;
    private String insoleMaterial;
    private String insideMaterial;
    private String topMaterial;
    private String material;
    private String lacingType;
}
