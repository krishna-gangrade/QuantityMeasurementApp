package com.apps.quantitymeasurement;

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
        return Math.round((value * conversionFactor) * 100.0) / 100.0;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return Math.round((baseValue / conversionFactor) * 100.0) / 100.0;
    }

    public String getUnitName() {
		return this.name();
	}
    
    public static void main(String[] args) {
        double kilograms = 1.0;
        double grams = WeightUnit.KILOGRAM.convertToBaseUnit(kilograms);
        System.out.println(kilograms + " KILOGRAM = " + grams + " GRAMS");

        double pounds = WeightUnit.POUND.convertFromBaseUnit(grams);
        System.out.println(grams + " GRAMS = " + pounds + " POUNDS");
    }
}
