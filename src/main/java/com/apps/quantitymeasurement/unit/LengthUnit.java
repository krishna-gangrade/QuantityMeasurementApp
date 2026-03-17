package com.apps.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurable {

    FEET(12.0),
    INCHES(1.0),
    YARDS(36.0),
    CENTIMETERS(1.0 / 2.54);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        double result = value * conversionFactor;
        return Math.round(result * 100.0) / 100.0;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        double result = baseValue / conversionFactor;
        return Math.round(result * 100.0) / 100.0;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public IMeasurable getUnitInstance(String name) {
        return LengthUnit.valueOf(name.toUpperCase());
    }

    /* ================= ARITHMETIC SUPPORT ================= */

    @Override
    public boolean supportsArithmetic() {
        return true;   // Length supports arithmetic
    }

    @Override
    public void validateOperationSupport(String operation) {
        // All operations allowed for length
    }

    /* ================= MEASUREMENT TYPE ================= */

    @Override
    public String getMeasurementType() {
        return "LENGTH";
    }
}