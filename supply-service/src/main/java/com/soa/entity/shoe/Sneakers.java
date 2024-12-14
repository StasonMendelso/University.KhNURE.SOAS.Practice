package com.soa.entity.shoe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Sneakers extends Shoe{
    private String lacingType;
    private String soleMaterial;
    private String insoleMaterial;
    private String topMaterial;
    private String insideMaterial;
}
