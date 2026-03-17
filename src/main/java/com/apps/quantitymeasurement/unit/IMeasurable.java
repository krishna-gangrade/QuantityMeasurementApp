
package com.apps.quantitymeasurement.unit;

public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();

    boolean supportsArithmetic();

    void validateOperationSupport(String operation);

    String getMeasurementType();   // force enums to implement this

    IMeasurable getUnitInstance(String name);
}
