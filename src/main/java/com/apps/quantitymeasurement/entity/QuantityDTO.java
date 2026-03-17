
package com.apps.quantitymeasurement.entity;

import com.apps.quantitymeasurement.unit.IMeasurable;

import java.util.function.Function;

public class QuantityDTO {

    private double value;
    private String unit;
    private String measurementType;

    public QuantityDTO() {}

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    /* ================= LENGTH ================= */

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
            return value * conversionFactor;
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / conversionFactor;
        }

        @Override
        public String getUnitName() {
            return name();
        }

        @Override
        public String getMeasurementType() {
            return "LENGTH";
        }

        @Override
        public IMeasurable getUnitInstance(String name) {
            return LengthUnit.valueOf(name.toUpperCase());
        }

        @Override
        public boolean supportsArithmetic() {
            return true;
        }

        @Override
        public void validateOperationSupport(String operation) {
            // allowed
        }
    }

    /* ================= WEIGHT ================= */

    public enum WeightUnit implements IMeasurable {

        MILLIGRAM(0.001),
        GRAM(1.0),
        KILOGRAM(1000.0),
        POUND(453.592),
        TONNE(1_000_000.0);

        private final double conversionFactor;

        WeightUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        @Override
        public double getConversionFactor() {
            return conversionFactor;
        }

        @Override
        public double convertToBaseUnit(double value) {
            return value * conversionFactor;
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / conversionFactor;
        }

        @Override
        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return "WEIGHT";
        }

        @Override
        public IMeasurable getUnitInstance(String name) {
            return WeightUnit.valueOf(name.toUpperCase());
        }

        @Override
        public boolean supportsArithmetic() {
            return true;
        }

        @Override
        public void validateOperationSupport(String operation) {
            // All arithmetic operations allowed
        }
    }

    /* ================= VOLUME ================= */

    public enum VolumeUnit implements IMeasurable {

        LITRE(1.0),
        MILLILITRE(0.001),
        GALLON(3.78541);

        private final double conversionFactor;

        VolumeUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        @Override
        public double getConversionFactor() {
            return conversionFactor;
        }

        @Override
        public double convertToBaseUnit(double value) {
            return value * conversionFactor;
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / conversionFactor;
        }

        @Override
        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return "VOLUME";
        }

        @Override
        public IMeasurable getUnitInstance(String name) {
            return VolumeUnit.valueOf(name.toUpperCase());
        }

        @Override
        public boolean supportsArithmetic() {
            return true;
        }

        @Override
        public void validateOperationSupport(String operation) {
            // All arithmetic operations allowed
        }
    }

    /* ================= TEMPERATURE ================= */

    public enum TemperatureUnit implements IMeasurable {

        CELSIUS(
                v -> v,
                v -> v
        ),
        FAHRENHEIT(
                v -> (v - 32) * 5 / 9,
                v -> v * 9 / 5 + 32
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
        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return "TEMPERATURE";
        }

        @Override
        public IMeasurable getUnitInstance(String name) {
            return TemperatureUnit.valueOf(name.toUpperCase());
        }

        @Override
        public boolean supportsArithmetic() {
            return false;
        }

        @Override
        public void validateOperationSupport(String operation) {

            if ("ADD".equalsIgnoreCase(operation) ||
                "SUBTRACT".equalsIgnoreCase(operation) ||
                "DIVIDE".equalsIgnoreCase(operation)) {

                throw new UnsupportedOperationException(
                        getMeasurementType() + " does not support " + operation + " operations."
                );
            }
        }
    }
}
