package com.soa.entity.shoe;

import com.soa.entity.ShoeType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author Stanislav Hlova
 */
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Shoe {
    private ObjectId id;
    private ObjectId originalShoeId;
    private String name;
    private String model;
    private String description;
    private LocalDateTime createdAt;
    private String manufacturerName;
    private ShoeType shoeType;
}
