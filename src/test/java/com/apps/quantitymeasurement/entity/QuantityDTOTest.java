
package com.apps.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuantityDTOTest {

    @Test
    void testConstructorAndGetters() {

        QuantityDTO dto = new QuantityDTO(10.5, "FEET", "Length");

        assertEquals(10.5, dto.getValue());
        assertEquals("FEET", dto.getUnit());
        assertEquals("Length", dto.getMeasurementType());
    }

    @Test
    void testDefaultConstructor() {

        QuantityDTO dto = new QuantityDTO();

        assertEquals(0.0, dto.getValue());
        assertNull(dto.getUnit());
        assertNull(dto.getMeasurementType());
    }

    @Test
    void testToString() {

        QuantityDTO dto = new QuantityDTO(5.0, "LITRE", "Volume");

        assertEquals("5.0 LITRE", dto.toString());
    }

    @Test
    void testNegativeValue() {

        QuantityDTO dto = new QuantityDTO(-10, "FEET", "Length");

        assertEquals(-10, dto.getValue());
    }

    @Test
    void testLargeValue() {

        QuantityDTO dto = new QuantityDTO(1e9, "GRAM", "Weight");

        assertEquals(1e9, dto.getValue());
    }

    @Test
    void testNullFields() {

        QuantityDTO dto = new QuantityDTO(5, null, null);

        assertNull(dto.getUnit());
        assertNull(dto.getMeasurementType());
    }
}
