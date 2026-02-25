package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2) {
		boolean result = quantity1.equals(quantity2);
		System.out.println("Are quantities equal? " + result);
		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {

		Quantity<U> result = quantity.convertTo(targetUnit);
		System.out.println(quantity + " = " + result);
		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1,
			Quantity<U> quantity2) {

		Quantity<U> result = quantity1.add(quantity2);
		System.out.println(quantity1 + " + " + quantity2 + " = " + result);
		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2,
			U targetUnit) {

		Quantity<U> result = quantity1.add(quantity2, targetUnit);
		System.out.println(quantity1 + " + " + quantity2 + " in " + targetUnit + " = " + result);
		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1,
			Quantity<U> quantity2) {

		Quantity<U> result = quantity1.subtract(quantity2);

		if (result.getValue() < 0)
			throw new IllegalArgumentException("Result cannot be negative");

		System.out.println(quantity1 + " - " + quantity2 + " = " + result);
		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1,
			Quantity<U> quantity2, U targetUnit) {

		Quantity<U> result = quantity1.subtract(quantity2, targetUnit);

		if (result.getValue() < 0)
			throw new IllegalArgumentException("Result cannot be negative");

		System.out.println(quantity1 + " - " + quantity2 + " in " + targetUnit + " = " + result);

		return result;
	}

	public static <U extends IMeasurable> double demonstrateDivision(Quantity<U> quantity1, Quantity<U> quantity2) {

		double result = quantity1.divide(quantity2);

		System.out.println(quantity1 + " / " + quantity2 + " = " + result);

		return result;
	}

	public static void main(String[] args) {

		Quantity<LengthUnit> lengthInFeet = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthInInches = new Quantity<>(12.0, LengthUnit.INCHES);
		boolean lengthEqual = demonstrateEquality(lengthInFeet, lengthInInches);
		System.out.println("Are lengths equal? " + lengthEqual);

		Quantity<LengthUnit> convertedLength = demonstrateConversion(lengthInFeet, LengthUnit.INCHES);
		System.out.println("Converted Length: " + convertedLength.getValue() + " " + convertedLength.getUnit());

		Quantity<LengthUnit> sumLength = demonstrateAddition(lengthInFeet, lengthInInches);
		System.out.println("Sum Length: " + sumLength.getValue() + " " + sumLength.getUnit());

		Quantity<LengthUnit> sumLengthInYards = demonstrateAddition(lengthInFeet, lengthInInches, LengthUnit.YARDS);
		System.out.println("Sum Length in Yards: " + sumLengthInYards.getValue() + " " + sumLengthInYards.getUnit());

		Quantity<LengthUnit> lSub1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> lSub2 = new Quantity<>(6.0, LengthUnit.INCHES);

		Quantity<LengthUnit> subLength = demonstrateSubtraction(lSub1, lSub2);
		System.out.println("Subtracted Length: " + subLength.getValue() + " " + subLength.getUnit());

		Quantity<LengthUnit> subLengthInInches = demonstrateSubtraction(lSub1, lSub2, LengthUnit.INCHES);
		System.out.println(
				"Subtracted Length in Inches: " + subLengthInInches.getValue() + " " + subLengthInInches.getUnit());

		double lengthDivision = demonstrateDivision(lSub1, lSub2);
		System.out.println("Length Division Result: " + lengthDivision);

		Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		boolean areEqual = demonstrateEquality(weightInGrams, weightInKilograms);
		System.out.println("Are weights equal? " + areEqual);

		Quantity<WeightUnit> convertedWeight = demonstrateConversion(weightInGrams, WeightUnit.KILOGRAM);
		System.out.println("Converted Weight: " + convertedWeight.getValue() + " " + convertedWeight.getUnit());

		Quantity<WeightUnit> weightInPounds = new Quantity<>(2.20462, WeightUnit.POUND);
		Quantity<WeightUnit> sumWeight = demonstrateAddition(weightInKilograms, weightInPounds);
		System.out.println("Sum Weight: " + sumWeight.getValue() + " " + sumWeight.getUnit());

		Quantity<WeightUnit> sumWeightInGrams = demonstrateAddition(weightInKilograms, weightInPounds, WeightUnit.GRAM);
		System.out.println("Sum Weight in Grams: " + sumWeightInGrams.getValue() + " " + sumWeightInGrams.getUnit());

		Quantity<WeightUnit> wSub1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> wSub2 = new Quantity<>(5000.0, WeightUnit.GRAM);

		Quantity<WeightUnit> subWeight = demonstrateSubtraction(wSub1, wSub2);
		System.out.println("Subtracted Weight: " + subWeight.getValue() + " " + subWeight.getUnit());

		Quantity<WeightUnit> subWeightInGrams = demonstrateSubtraction(wSub1, wSub2, WeightUnit.GRAM);
		System.out.println(
				"Subtracted Weight in Grams: " + subWeightInGrams.getValue() + " " + subWeightInGrams.getUnit());

		double weightDivision = demonstrateDivision(wSub1, wSub2);
		System.out.println("Weight Division Result: " + weightDivision);

		// Volume Demonstration

		Quantity<VolumeUnit> volumeInLitre = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> volumeInMillilitre = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> volumeInGallon = new Quantity<>(1.0, VolumeUnit.GALLON);
		boolean volumeEqual = demonstrateEquality(volumeInLitre, volumeInMillilitre);
		System.out.println("Are volumes equal? " + volumeEqual);

		Quantity<VolumeUnit> convertedVolume = demonstrateConversion(volumeInGallon, VolumeUnit.LITRE);
		System.out.println("Converted Volume: " + convertedVolume.getValue() + " " + convertedVolume.getUnit());

		Quantity<VolumeUnit> sumVolume = demonstrateAddition(volumeInLitre, volumeInMillilitre);
		System.out.println("Sum Volume: " + sumVolume.getValue() + " " + sumVolume.getUnit());

		Quantity<VolumeUnit> sumVolumeInGallon = demonstrateAddition(volumeInLitre, volumeInMillilitre,
				VolumeUnit.GALLON);
		System.out.println("Sum Volume in Gallon: " + sumVolumeInGallon.getValue() + " " + sumVolumeInGallon.getUnit());

		Quantity<VolumeUnit> vSub1 = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> vSub2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> subVolume = demonstrateSubtraction(vSub1, vSub2);
		System.out.println("Subtracted Volume: " + subVolume.getValue() + " " + subVolume.getUnit());

		Quantity<VolumeUnit> subVolumeInML = demonstrateSubtraction(vSub1, vSub2, VolumeUnit.MILLILITRE);
		System.out.println(
				"Subtracted Volume in Millilitre: " + subVolumeInML.getValue() + " " + subVolumeInML.getUnit());

		double volumeDivision = demonstrateDivision(vSub1, vSub2);
		System.out.println("Volume Division Result: " + volumeDivision);
	}
}
