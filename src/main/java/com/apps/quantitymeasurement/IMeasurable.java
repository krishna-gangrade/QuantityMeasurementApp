package com.apps.quantitymeasurement;

public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);
    
    public String getUnitName();
    
    SupportsArithmetic supportsArithmetic = () -> true;

	default boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}
	default void validateOperationSupport(String operation) {
	}
}
