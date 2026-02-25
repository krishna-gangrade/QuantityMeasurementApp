
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
		double result = value * conversionFactor;
		return Math.round(result * 1_000_000.0) / 1_000_000.0;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		double result = baseValue / conversionFactor;
		return Math.round(result * 1_000_000.0) / 1_000_000.0;
	}
	
	@Override
    public String getUnitName() {
        return name();
    }
}
