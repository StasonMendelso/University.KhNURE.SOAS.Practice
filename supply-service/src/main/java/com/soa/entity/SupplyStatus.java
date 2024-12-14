package com.soa.entity;

/**
 * @author Stanislav Hlova
 */
public enum SupplyStatus {
    ON_DELIVERY("On delivery"), SHIPPED_TO_WAREHOUSE("Shipped to warehouse");

    public final String value;

    SupplyStatus(String value) {
        this.value = value;
    }

    public static SupplyStatus valueFrom(String shoeType) {
        for (SupplyStatus value : SupplyStatus.values()) {
            if (value.value.equals(shoeType)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No supply status for passed value \"" + shoeType + "\" was found.");
    }
}
