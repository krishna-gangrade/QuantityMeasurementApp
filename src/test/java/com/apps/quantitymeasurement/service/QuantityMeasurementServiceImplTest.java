
package com.apps.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;

public class QuantityMeasurementServiceImplTest {

    private QuantityMeasurementServiceImpl service;

    private static final double EPSILON = 0.0001;

    @BeforeEach
    void setup() {

        service = new QuantityMeasurementServiceImpl(
                QuantityMeasurementCacheRepository.getInstance());
    }

    // =========================
    // COMPARISON TESTS
    // =========================

    @Test
    void testCompare_EquivalentUnits() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "Length");

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void testCompare_DifferentUnits() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(10, "INCHES", "Length");

        assertFalse(service.compare(q1, q2));
    }

    @Test
    void testCompare_VolumeUnits() {

        QuantityDTO q1 = new QuantityDTO(1, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(1000, "MILLILITRE", "Volume");

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void testCompare_NegativeValues() {

        QuantityDTO q1 = new QuantityDTO(-1, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(-1000, "MILLILITRE", "Volume");

        assertTrue(service.compare(q1, q2));
    }

    // =========================
    // CONVERSION TESTS
    // =========================

    @Test
    void testConvert_Length() {

        QuantityDTO q = new QuantityDTO(1, "FEET", "Length");

        QuantityDTO result = service.convert(q, "INCHES");

        assertEquals(12.0, result.getValue(), EPSILON);
    }

    @Test
    void testConvert_Weight() {

        QuantityDTO q = new QuantityDTO(1, "KILOGRAM", "Weight");

        QuantityDTO result = service.convert(q, "GRAM");

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testConvert_LargeValues() {

        QuantityDTO q = new QuantityDTO(1e6, "GRAM", "Weight");

        QuantityDTO result = service.convert(q, "KILOGRAM");

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    // =========================
    // ADDITION TESTS
    // =========================

    @Test
    void testAdd_LengthUnits() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "Length");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAdd_VolumeUnits() {

        QuantityDTO q1 = new QuantityDTO(1, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(1000, "MILLILITRE", "Volume");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAdd_LargeValues() {

        QuantityDTO q1 = new QuantityDTO(1e6, "GRAM", "Weight");
        QuantityDTO q2 = new QuantityDTO(1e6, "GRAM", "Weight");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(2e6, result.getValue(), EPSILON);
    }

    @Test
    void testAdd_NegativeValues() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(-5, "FEET", "Length");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    // =========================
    // ADDITION WITH TARGET UNIT
    // =========================

    @Test
    void testAdd_TargetUnit_Length() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "Length");

        QuantityDTO result = service.add(q1, q2, "INCHES");

        assertEquals(24.0, result.getValue(), EPSILON);
    }

    @Test
    void testAdd_TargetUnit_Volume() {

        QuantityDTO q1 = new QuantityDTO(1, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(1, "LITRE", "Volume");

        QuantityDTO result = service.add(q1, q2, "MILLILITRE");

        assertEquals(2000.0, result.getValue(), EPSILON);
    }

    // =========================
    // SUBTRACTION TESTS
    // =========================

    @Test
    void testSubtract_Length() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "Length");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtract_CrossUnits() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(6, "INCHES", "Length");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(9.5, result.getValue(), EPSILON);
    }

    @Test
    void testSubtract_NegativeResult() {

        QuantityDTO q1 = new QuantityDTO(2, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "Length");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(-3.0, result.getValue(), EPSILON);
    }

    // =========================
    // DIVISION TESTS
    // =========================

    @Test
    void testDivide_LengthUnits() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "Length");

        assertEquals(2.0, service.divide(q1, q2), EPSILON);
    }

    @Test
    void testDivide_CrossUnits() {

        QuantityDTO q1 = new QuantityDTO(24, "INCHES", "Length");
        QuantityDTO q2 = new QuantityDTO(2, "FEET", "Length");

        assertEquals(1.0, service.divide(q1, q2), EPSILON);
    }

    @Test
    void testDivide_LargeNumbers() {

        QuantityDTO q1 = new QuantityDTO(1e9, "GRAM", "Weight");
        QuantityDTO q2 = new QuantityDTO(1e3, "GRAM", "Weight");

        assertEquals(1e6, service.divide(q1, q2), EPSILON);
    }

    // =========================
    // EDGE CASES
    // =========================

    @Test
    void testDivisionByZero() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(0, "FEET", "Length");

        assertThrows(Exception.class,
                () -> service.divide(q1, q2));
    }

    @Test
    void testInvalidUnit() {

        QuantityDTO q1 = new QuantityDTO(10, "INVALID", "Length");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "Length");

        assertThrows(Exception.class,
                () -> service.add(q1, q2));
    }

    @Test
    void testNullDTO() {

        QuantityDTO q = new QuantityDTO(10, "FEET", "Length");

        assertThrows(Exception.class,
                () -> service.add(q, null));
    }

    // =========================
    // TEMPERATURE RESTRICTIONS
    // =========================

    @Test
    void testAddTemperature_ShouldFail() {

        QuantityDTO q1 = new QuantityDTO(100, "CELSIUS", "Temperature");
        QuantityDTO q2 = new QuantityDTO(50, "CELSIUS", "Temperature");

        assertThrows(Exception.class,
                () -> service.add(q1, q2));
    }

    @Test
    void testSubtractTemperature_ShouldFail() {

        QuantityDTO q1 = new QuantityDTO(100, "CELSIUS", "Temperature");
        QuantityDTO q2 = new QuantityDTO(50, "CELSIUS", "Temperature");

        assertThrows(Exception.class,
                () -> service.subtract(q1, q2));
    }

    @Test
    void testDivideTemperature_ShouldFail() {

        QuantityDTO q1 = new QuantityDTO(100, "CELSIUS", "Temperature");
        QuantityDTO q2 = new QuantityDTO(50, "CELSIUS", "Temperature");

        assertThrows(Exception.class,
                () -> service.divide(q1, q2));
    }

}
