package com.soa.dto.shoe;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
public class SlippersDto extends ShoeDto{
    private String soleMaterial;
    private String insoleMaterial;
    private String material;
}
