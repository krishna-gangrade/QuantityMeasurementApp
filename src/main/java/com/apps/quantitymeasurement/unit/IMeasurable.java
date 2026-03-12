
package com.apps.quantitymeasurement.unit;

public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();

    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
        // default allows operation
    }

    // Default measurement type = enum name
    default String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    // Default implementation (can be overridden if needed)
    default IMeasurable getUnitInstance(String name) {
        throw new UnsupportedOperationException("Unit lookup not implemented");
    }
}
