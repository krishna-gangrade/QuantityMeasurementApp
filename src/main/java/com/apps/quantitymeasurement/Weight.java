package com.apps.quantitymeasurement;

public class Weight {

    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 1e-6;

    public Weight(double value, WeightUnit unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Weight that = (Weight) obj;
        return compare(that);
    }

    public Weight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseValue = convertToBaseUnit();
        double converted = convertFromBaseToTargetUnit(baseValue, targetUnit);
        return new Weight(converted, targetUnit);
    }

    public Weight add(Weight thatWeight) {
        if (thatWeight == null)
            throw new IllegalArgumentException("Operand cannot be null");

        double sumInBase = this.convertToBaseUnit() + thatWeight.convertToBaseUnit();
        double result = convertFromBaseToTargetUnit(sumInBase, this.unit);
        return new Weight(result, this.unit);
    }

    public Weight add(Weight thatWeight, WeightUnit targetUnit) {
        if (thatWeight == null)
            throw new IllegalArgumentException("Operand cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        return addAndConvert(thatWeight, targetUnit);
    }

    private boolean compare(Weight thatWeight) {
        double thisBase = this.convertToBaseUnit();
        double thatBase = thatWeight.convertToBaseUnit();
        return Math.abs(thisBase - thatBase) < EPSILON;
    }

    private Weight addAndConvert(Weight weight, WeightUnit targetUnit) {
        double sumInBase = this.convertToBaseUnit() + weight.convertToBaseUnit();
        double result = convertFromBaseToTargetUnit(sumInBase, targetUnit);
        return new Weight(result, targetUnit);
    }

    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    private double convertFromBaseToTargetUnit(double weightInGrams, WeightUnit targetUnit) {
        return targetUnit.convertFromBaseUnit(weightInGrams);
    }

    @Override
    public int hashCode() {
        long normalized = Math.round(convertToBaseUnit() / EPSILON);
        return Long.hashCode(normalized);
    }

    @Override
    public String toString() {
        return String.format("%s %s",
                Double.toString(value).replaceAll("\\.0+$", ""),
                unit);
    }

    public static void main(String[] args) {

        Weight w1 = new Weight(1000, WeightUnit.GRAM);
        Weight w2 = new Weight(1, WeightUnit.KILOGRAM);

        System.out.println(w1 + " == " + w2 + " ? " + w1.equals(w2));

        Weight converted = w2.convertTo(WeightUnit.POUND);
        System.out.println(w2 + " -> " + converted);

        Weight sum1 = w1.add(w2);
        System.out.println(w1 + " + " + w2 + " = " + sum1);

        Weight sum2 = w1.add(w2, WeightUnit.KILOGRAM);
        System.out.println(w1 + " + " + w2 + " in KILOGRAM = " + sum2);
    }
}