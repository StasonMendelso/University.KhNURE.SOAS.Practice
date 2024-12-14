package com.soa.dto.shoe;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
public class SneakersDto extends ShoeDto {
    private String lacingType;
    private String soleMaterial;
    private String insoleMaterial;
    private String topMaterial;
    private String insideMaterial;
}
