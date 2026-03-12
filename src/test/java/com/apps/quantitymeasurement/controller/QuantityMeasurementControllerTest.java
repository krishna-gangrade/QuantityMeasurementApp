package com.apps.quantitymeasurement.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementControllerTest {

    private QuantityMeasurementController controller;

    @BeforeEach
    void setup() {

        controller = new QuantityMeasurementController(
                new QuantityMeasurementServiceImpl(
                        QuantityMeasurementCacheRepository.getInstance()));
    }

    private static final double EPSILON = 0.0001;

    // -------------------------
    // COMPARISON TESTS
    // -------------------------

    @Test
    void testComparison_EquivalentUnits() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "Length");

        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    void testComparison_DifferentUnits() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(10.0, "INCHES", "Length");

        assertFalse(controller.performComparison(q1, q2));
    }

    @Test
    void testComparison_ZeroValues() {

        QuantityDTO q1 = new QuantityDTO(0.0, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(0.0, "MILLILITRE", "Volume");

        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    void testComparison_NegativeValues() {

        QuantityDTO q1 = new QuantityDTO(-1.0, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(-1000.0, "MILLILITRE", "Volume");

        assertTrue(controller.performComparison(q1, q2));
    }

    // -------------------------
    // ADDITION TESTS
    // -------------------------

    @Test
    void testAddition_CrossUnits() {

        QuantityDTO q1 = new QuantityDTO(1.0, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(1000.0, "MILLILITRE", "Volume");

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_LargeValues() {

        QuantityDTO q1 = new QuantityDTO(1e6, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(1e6, "LITRE", "Volume");

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(2e6, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_NegativeNumbers() {

        QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(-5.0, "FEET", "Length");

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_WithTargetUnit() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "Length");

        QuantityDTO result =
                controller.performAddition(q1, q2, "INCHES");

        assertEquals(24.0, result.getValue(), EPSILON);
    }
    
    @Test
    void testAddition_TargetUnit_LengthFeetToInches() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "Length");

        QuantityDTO result =
                controller.performAddition(q1, q2, "INCHES");

        assertEquals(24.0, result.getValue(), EPSILON);
    }
    
    @Test
    void testAddition_TargetUnit_LengthInchesToFeet() {

        QuantityDTO q1 = new QuantityDTO(12.0, "INCHES", "Length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "Length");

        QuantityDTO result =
                controller.performAddition(q1, q2, "FEET");

        assertEquals(2.0, result.getValue(), EPSILON);
    }
    
    @Test
    void testAddition_TargetUnit_VolumeLitresToMillilitres() {

        QuantityDTO q1 = new QuantityDTO(1.0, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(1.0, "LITRE", "Volume");

        QuantityDTO result =
                controller.performAddition(q1, q2, "MILLILITRE");

        assertEquals(2000.0, result.getValue(), EPSILON);
    }
    
    @Test
    void testAddition_TargetUnit_WeightKgToGram() {

        QuantityDTO q1 = new QuantityDTO(1.0, "KILOGRAM", "Weight");
        QuantityDTO q2 = new QuantityDTO(1.0, "KILOGRAM", "Weight");

        QuantityDTO result =
                controller.performAddition(q1, q2, "GRAM");

        assertEquals(2000.0, result.getValue(), EPSILON);
    }
    
    @Test
    void testAddition_TargetUnit_WithNegativeValues() {

        QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(-5.0, "FEET", "Length");

        QuantityDTO result =
                controller.performAddition(q1, q2, "FEET");

        assertEquals(5.0, result.getValue(), EPSILON);
    }
    
    @Test
    void testAddition_TargetUnit_WithZero() {

        QuantityDTO q1 = new QuantityDTO(5.0, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(0.0, "MILLILITRE", "Volume");

        QuantityDTO result =
                controller.performAddition(q1, q2, "LITRE");

        assertEquals(5.0, result.getValue(), EPSILON);
    }
    
    @Test
    void testControllerAddTemperature_ShouldFail() {

        QuantityDTO q1 = new QuantityDTO(100.0, "CELSIUS", "Temperature");
        QuantityDTO q2 = new QuantityDTO(50.0, "CELSIUS", "Temperature");

        assertThrows(Exception.class,
                () -> controller.performAddition(q1, q2));
    }

    // -------------------------
    // SUBTRACTION TESTS
    // -------------------------

    @Test
    void testSubtraction_Normal() {

        QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(2.0, "FEET", "Length");

        QuantityDTO result = controller.performSubtraction(q1, q2);

        assertEquals(8.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_ResultNegative() {

        QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(5.0, "FEET", "Length");

        QuantityDTO result = controller.performSubtraction(q1, q2);

        assertEquals(-3.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_CrossUnits() {

        QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "Length");

        QuantityDTO result = controller.performSubtraction(q1, q2);

        assertEquals(9.0, result.getValue(), EPSILON);
    }

    // -------------------------
    // DIVISION TESTS
    // -------------------------

    @Test
    void testDivision_Normal() {

        QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(5.0, "FEET", "Length");

        assertEquals(2.0, controller.performDivision(q1, q2), EPSILON);
    }

    @Test
    void testDivision_Fraction() {

        QuantityDTO q1 = new QuantityDTO(5.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(10.0, "FEET", "Length");

        assertEquals(0.5, controller.performDivision(q1, q2), EPSILON);
    }

    @Test
    void testDivision_LargeNumbers() {

        QuantityDTO q1 = new QuantityDTO(1e9, "GRAM", "Weight");
        QuantityDTO q2 = new QuantityDTO(1e3, "GRAM", "Weight");

        assertEquals(1e6, controller.performDivision(q1, q2), EPSILON);
    }

    // -------------------------
    // CONVERSION TESTS
    // -------------------------

    @Test
    void testConversion_Length() {

        QuantityDTO q = new QuantityDTO(1.0, "FEET", "Length");

        QuantityDTO result =
                controller.performConversion(q, "INCHES");

        assertEquals(12.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_Weight() {

        QuantityDTO q = new QuantityDTO(1.0, "KILOGRAM", "Weight");

        QuantityDTO result =
                controller.performConversion(q, "GRAM");

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    // -------------------------
    // EXTREME EDGE CASES
    // -------------------------

    @Test
    void testNullOperandAddition() {

        QuantityDTO q = new QuantityDTO(10.0, "FEET", "Length");

        assertThrows(Exception.class,
                () -> controller.performAddition(q, null));
    }

    @Test
    void testNullOperandSubtraction() {

        QuantityDTO q = new QuantityDTO(10.0, "FEET", "Length");

        assertThrows(Exception.class,
                () -> controller.performSubtraction(q, null));
    }

    @Test
    void testDivisionByZero() {

        QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(0.0, "FEET", "Length");

        assertThrows(Exception.class,
                () -> controller.performDivision(q1, q2));
    }

}
