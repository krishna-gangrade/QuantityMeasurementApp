# QuantityMeasurementApp

## 🗓 UC1: Feet measurement equality
*(Date: 17-Feb-2026)*

- Creating Feet class which is responsible for checking the equality of two numerical values
measured in feet in the Quantity Measurement Application.
- Creating JUnit test cases : 
  - testEquality_SameValue()
  - testEquality_DifferentValue()
  - testEquality_NullComparison()
  - testEquality_NonNumericInput()
  - testEquality_SameReference()

🔗 *Code Link:*  
[UC1: Feet measurement equality](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC1-FleetEquality)

## 🗓 UC2: Feet and Inches measurement equality
*(Date: 18-Feb-2026)*

- Creating Inchs class which is responsible for checking the equality of two numerical values
measured in feet in the Quantity Measurement Application.
- Creating JUnit test cases : 
  - testEquality_SameValue()
  - testEquality_DifferentValue()
  - testEquality_NullComparison()
  - testEquality_NonNumericInput()
  - testEquality_SameReference()

🔗 *Code Link:*  
[UC2: Feet and Inches measurement equality](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC2-InchEquality)

## 🗓 UC3: Generic Quantity Class equality
*(Date: 18-Feb-2026)*

- Main Flow
  - User inputs two numerical values with their respective unit types.
  - The Quantity Length class validates the input values to ensure they are numeric.
  - The Quantity Length class validates the unit type against supported units.
  - Both values are converted to a common base unit (e.g., feet) using conversion factors.
  - The converted values are compared for equality.
  - The result of the comparison is returned to the user.
- Creating JUnit test cases : 
  - testEquality_FeetToFeet_SameValue()
  - testEquality_InchToInch_SameValue()
  - testEquality_NullComparison()
  - testEquality_InchToFeet_EquivalentValue()
  - testEquality_FeetToFeet_DifferentValue()
  - testEquality_InchToInch_DifferentValue()

🔗 *Code Link:*  
[UC3: Generic Quantity Class for DRY Principle](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC3-GenericLength)

## 🗓 UC4: Extended Unit Support
*(Date: 20-Feb-2026)*

- Main Flow
  - Users input two numerical values with their respective unit types (feet, inches, yards or cms).
  - The Quantity Length class validates the input values to ensure they are numeric.
  - The QuantityLength class validates the unit type against supported units (feet, inches, yards, cms).
  - Both values are converted to a common base unit (in or feet) using conversion factors.
  - The converted values are compared for equality.
  - The result of the comparison is returned to the user.
- Creating JUnit test cases : 
  - testEquality_YardToYard_SameValue()
  - testEquality_YardToYard_DifferentValue()
  - testEquality_YardToFeet_EquivalentValue()
  - testEquality_FeetToYard_EquivalentValue()
  - testEquality_YardToInches_EquivalentValue()
  - testEquality_InchesToYard_EquivalentValue()

🔗 *Code Link:*  
[UC4: Extended Unit Support](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC4-YardEquality)

## 🗓 UC5: Unit-to-Unit Conversion
*(Date: 20-Feb-2026)*

- Extending the Quantity Length class to provide explicit unit-to-unit conversion functionality.
- Introducing a public API method :
  - static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit)
- Supporting conversions between units (feet, inches, yards or centimeters).
- The Quantity Length class validates the input value to ensure it is finite (not NaN or Infinite).
- The Quantity Length class validates that source and target units are non-null and supported.
- The input value is converted to a common base unit (feet).
- The base unit value is converted to the target unit using conversion factors.
- Floating-point precision is handled using epsilon tolerance.
- Invalid inputs throw IllegalArgumentException.

- Creating JUnit test cases :
  - testConversion_FeetToInches()
  - testConversion_InchesToFeet()
  - testConversion_YardsToInches()
  - testConversion_InchesToYards()
  - testConversion_CentimetersToInches()
  - testConversion_FeetToYard()
  - testConversion_RoundTrip_PreservesValue()
  - testConversion_ZeroValue()
  - testConversion_NegativeValue()
  - testConversion_InvalidUnit_Throws()
  - testConversion_NaNOrInfinite_Throws()
  - testConversion_PrecisionTolerance()

🔗 *Code Link:*  
[UC5: Unit-to-Unit Conversion](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC5-UnitConversion)

## 🗓 UC6: Addition of Two Length Units
*(Date: 22-Feb-2026)*

- Extending UC5 by introducing addition operations between two length measurements.
- Enabling addition of two Quantity Length objects even if they belong to different units (same category: Length).
- The result is returned in the unit of the first operand.
- Validating:
  - Both operands are non-null.
  - Units are valid and belong to LengthUnit enum.
  - Values are finite numbers (not NaN or Infinite).
- Converting both operands to a common base unit (feet).
- Adding the normalized values.
- Converting the sum back to the unit of the first operand.
- Returning a new Quantity Length object (immutability principle).
- Throwing IllegalArgumentException for invalid inputs.

- Creating JUnit test cases :
  - testAddition_SameUnit_FeetPlusFeet()
  - testAddition_SameUnit_InchPlusInch()
  - testAddition_CrossUnit_FeetPlusInches()
  - testAddition_CrossUnit_InchPlusFeet()
  - testAddition_CrossUnit_YardPlusFeet()
  - testAddition_CrossUnit_CentimeterPlusInch()
  - testAddition_Commutativity()
  - testAddition_WithZero()
  - testAddition_NegativeValues()
  - testAddition_NullSecondOperand()
  - testAddition_LargeValues()
  - testAddition_SmallValues()

🔗 *Code Link:*  
[UC6: Addition of Two Length Units](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC6-UnitAddition)

