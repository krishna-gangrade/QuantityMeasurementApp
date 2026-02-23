package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.apps.quantitymeasurement.QuantityMeasurementApp.*;

public class QuantityMeasurementAppTest {

    @Test
    void testFeetEquality_SameValue() {
        QuantityMeasurementApp.Feet f1 = new Feet(1.0);
        QuantityMeasurementApp.Feet f2 = new Feet(1.0);

        assertTrue(f1.equals(f2));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        QuantityMeasurementApp.Feet f1 = new Feet(1.0);
        QuantityMeasurementApp.Feet f2 = new Feet(2.0);

        assertFalse(f1.equals(f2));
    }

    @Test
    void testFeetEquality_NullComparison() {
    	QuantityMeasurementApp.Feet f1 = new Feet(1.0);

        assertFalse(f1.equals(null));
    }

    @Test
    void testFeetEquality_DifferentClass() {
    	QuantityMeasurementApp.Feet f1 = new Feet(1.0);

        assertFalse(f1.equals("1.0"));
    }

    @Test
    void testFeetEquality_SameReference() {
    	QuantityMeasurementApp.Feet f1 = new Feet(1.0);

        assertTrue(f1.equals(f1));
    }
    @Test
    void testInchesEquality_SameValue() {
        QuantityMeasurementApp.Inches f1 = new Inches(1.0);
        QuantityMeasurementApp.Inches f2 = new Inches(1.0);

        assertTrue(f1.equals(f2));
    }

    @Test
    void testInchesEquality_DifferentValue() {
        QuantityMeasurementApp.Inches f1 = new Inches(1.0);
        QuantityMeasurementApp.Inches f2 = new Inches(2.0);

        assertFalse(f1.equals(f2));
    }

    @Test
    void testInchesEquality_NullComparison() {
    	QuantityMeasurementApp.Inches f1 = new Inches(1.0);

        assertFalse(f1.equals(null));
    }

    @Test
    void testInchesEquality_DifferentClass() {
    	QuantityMeasurementApp.Inches f1 = new Inches(1.0);

        assertFalse(f1.equals("1.0"));
    }

    @Test
    void testInchesEquality_SameReference() {
    	QuantityMeasurementApp.Inches f1 = new Inches(1.0);

        assertTrue(f1.equals(f1));
    }
}
