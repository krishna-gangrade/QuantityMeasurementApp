
package com.apps.quantitymeasurement.unit;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            v -> v,
            v -> v
    ),
    FAHRENHEIT(
            v -> (v - 32.0) * 5.0 / 9.0,
            v -> v * 9.0 / 5.0 + 32.0
    ),
    KELVIN(
            v -> v - 273.15,
            v -> v + 273.15
    );

    private final Function<Double, Double> toBase;
    private final Function<Double, Double> fromBase;

    TemperatureUnit(Function<Double, Double> toBase,
                    Function<Double, Double> fromBase) {
        this.toBase = toBase;
        this.fromBase = fromBase;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toBase.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromBase.apply(baseValue);
    }

    @Override
    public boolean supportsArithmetic() {
        return false;   // temperature cannot be added/subtracted/divided
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                getMeasurementType() + " does not support " + operation + " operations."
        );
    }

    @Override
    public IMeasurable getUnitInstance(String name) {
        return TemperatureUnit.valueOf(name.toUpperCase());
    }

    @Override
    public String getMeasurementType() {
        return "TEMPERATURE";
    }
}
