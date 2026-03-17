
package com.apps.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.unit.LengthUnit;

class QuantityModelTest {

    @Test
    void testConstructorAndGetters() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(10, LengthUnit.FEET);

        assertEquals(10, model.getValue());
        assertEquals(LengthUnit.FEET, model.getUnit());
    }

    @Test
    void testNegativeValue() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(-5, LengthUnit.FEET);

        assertEquals(-5, model.getValue());
    }

    @Test
    void testZeroValue() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(0, LengthUnit.FEET);

        assertEquals(0, model.getValue());
    }

    @Test
    void testLargeValue() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(1e9, LengthUnit.FEET);

        assertEquals(1e9, model.getValue());
    }

    @Test
    void testNullUnit() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(10, null);

        assertNull(model.getUnit());
    }
}
