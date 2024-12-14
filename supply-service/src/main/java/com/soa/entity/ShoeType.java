package com.soa.entity;

/**
 * @author Stanislav Hlova
 */
public enum ShoeType {
    SNEAKERS("Sneakers"), HEELS("Heels"), SLIPPERS("Slippers");
    public final String value;

    ShoeType(String value) {
        this.value = value;
    }

    public static ShoeType valueFrom(String shoeType) {
        for (ShoeType value : ShoeType.values()) {
            if (value.value.equals(shoeType)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No shoe type for passed value \"" + shoeType + "\" was found.");
    }
}
