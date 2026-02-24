package com.apps.quantitymeasurement;

public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);
    
    public String getUnitName();

    public static void main(String[] args) {
        System.out.println("IMeasurable Interface");
    }
}