package com.soa.dto.shoe;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
public class HeelsDto extends ShoeDto {
    private String soleMaterial;
    private String insoleMaterial;
    private String insideMaterial;
    private String topMaterial;
}
