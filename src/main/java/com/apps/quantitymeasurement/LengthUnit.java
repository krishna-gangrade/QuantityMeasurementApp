
package com.apps.quantitymeasurement;

public enum LengthUnit implements IMeasurable {

    FEET(12.0),
    INCHES(1.0),
    YARDS(36.0),
    CENTIMETERS(1.0/2.42);

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
        double feet = 1.0;
        double inches = LengthUnit.FEET.convertToBaseUnit(feet);
        System.out.println(feet + " FEET = " + inches + " INCHES");

        double yards = LengthUnit.YARDS.convertFromBaseUnit(inches);
        System.out.println(inches + " INCHES = " + yards + " YARDS");
    }

	
}
