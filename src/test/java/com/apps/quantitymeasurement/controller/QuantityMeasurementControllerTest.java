
package com.apps.quantitymeasurement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

class QuantityMeasurementControllerTest {

    private QuantityMeasurementController controller;

    private IQuantityMeasurementService service;

    private static final double EPSILON = 0.0001;

    @BeforeEach
    void setup() {

        service = Mockito.mock(IQuantityMeasurementService.class);

        controller = new QuantityMeasurementController(service);
    }

    // =========================
    // COMPARISON TEST
    // =========================

    @Test
    void testComparisonEquivalentUnits() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "Length");

        when(service.compare(q1, q2)).thenReturn(true);

        boolean result = controller.performComparison(q1, q2);

        assertTrue(result);

        verify(service).compare(q1, q2);
    }

    // =========================
    // ADDITION TEST
    // =========================

    @Test
    void testAddition() {

        QuantityDTO q1 = new QuantityDTO(1.0, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(1000.0, "MILLILITRE", "Volume");

        QuantityDTO expected = new QuantityDTO(2.0, "LITRE", "Volume");

        when(service.add(q1, q2)).thenReturn(expected);

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(2.0, result.getValue(), EPSILON);

        verify(service).add(q1, q2);
    }

    // =========================
    // ADDITION WITH TARGET
    // =========================

    @Test
    void testAdditionWithTargetUnit() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "Length");

        QuantityDTO expected = new QuantityDTO(24.0, "INCHES", "Length");

        when(service.add(q1, q2, "INCHES")).thenReturn(expected);

        QuantityDTO result = controller.performAddition(q1, q2, "INCHES");

        assertEquals(24.0, result.getValue(), EPSILON);

        verify(service).add(q1, q2, "INCHES");
    }

    // =========================
    // SUBTRACTION TEST
    // =========================

    @Test
    void testSubtraction() {

        QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(2.0, "FEET", "Length");

        QuantityDTO expected = new QuantityDTO(8.0, "FEET", "Length");

        when(service.subtract(q1, q2)).thenReturn(expected);

        QuantityDTO result = controller.performSubtraction(q1, q2);

        assertEquals(8.0, result.getValue(), EPSILON);

        verify(service).subtract(q1, q2);
    }

    // =========================
    // DIVISION TEST
    // =========================

    @Test
    void testDivision() {

        QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(5.0, "FEET", "Length");

        when(service.divide(q1, q2)).thenReturn(2.0);

        double result = controller.performDivision(q1, q2);

        assertEquals(2.0, result, EPSILON);

        verify(service).divide(q1, q2);
    }

    // =========================
    // CONVERSION TEST
    // =========================

    @Test
    void testConversion() {

        QuantityDTO q = new QuantityDTO(1.0, "FEET", "Length");

        QuantityDTO expected = new QuantityDTO(12.0, "INCHES", "Length");

        when(service.convert(q, "INCHES")).thenReturn(expected);

        QuantityDTO result = controller.performConversion(q, "INCHES");

        assertEquals(12.0, result.getValue(), EPSILON);

        verify(service).convert(q, "INCHES");
    }

    // =========================
    // EXCEPTION TEST
    // =========================

    @Test
    void testAdditionThrowsException() {

        QuantityDTO q1 = new QuantityDTO(10.0, "CELSIUS", "Temperature");
        QuantityDTO q2 = new QuantityDTO(50.0, "CELSIUS", "Temperature");

        when(service.add(q1, q2)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class,
                () -> controller.performAddition(q1, q2));
    }
}
