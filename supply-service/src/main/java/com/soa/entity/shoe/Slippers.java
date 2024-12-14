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
public class Slippers extends Shoe{
    private String soleMaterial;
    private String insoleMaterial;
    private String material;
}
