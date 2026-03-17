
package com.apps.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.*;

class QuantityMeasurementEntityTest {

    // ======================================
    // NORMAL ENTITY CONSTRUCTOR TEST
    // ======================================

    @Test
    void testNormalConstructor() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        10, "FEET",
                        5, "FEET",
                        "ADD",
                        15
                );

        assertEquals(10, entity.getThisValue());
        assertEquals("FEET", entity.getThisUnit());

        assertEquals(5, entity.getThatValue());
        assertEquals("FEET", entity.getThatUnit());

        assertEquals("ADD", entity.getOperation());

        assertEquals(15, entity.getResultValue());

        assertFalse(entity.isError());
    }

    // ======================================
    // ERROR ENTITY CONSTRUCTOR TEST
    // ======================================

    @Test
    void testErrorConstructor() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "ADD",
                        "Invalid operation"
                );

        assertTrue(entity.isError());

        assertEquals("ADD", entity.getOperation());

        assertEquals("Invalid operation",
                entity.getErrorMessage());
    }

    // ======================================
    // EXTREME VALUES TEST
    // ======================================

    @Test
    void testExtremeValues() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        1e9, "GRAM",
                        1e9, "GRAM",
                        "ADD",
                        2e9
                );

        assertEquals(2e9, entity.getResultValue());
    }

    // ======================================
    // NEGATIVE VALUES TEST
    // ======================================

    @Test
    void testNegativeValues() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        -10, "FEET",
                        -5, "FEET",
                        "SUBTRACT",
                        -5
                );

        assertEquals(-5, entity.getResultValue());
    }

    // ======================================
    // ZERO VALUES TEST
    // ======================================

    @Test
    void testZeroValues() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        0, "FEET",
                        0, "FEET",
                        "ADD",
                        0
                );

        assertEquals(0, entity.getResultValue());
    }

    // ======================================
    // SERIALIZATION TEST
    // ======================================

    @Test
    void testSerialization() throws Exception {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        10, "FEET",
                        5, "FEET",
                        "ADD",
                        15
                );

        ByteArrayOutputStream bos =
                new ByteArrayOutputStream();

        ObjectOutputStream out =
                new ObjectOutputStream(bos);

        out.writeObject(entity);
        out.flush();

        byte[] bytes = bos.toByteArray();

        ObjectInputStream in =
                new ObjectInputStream(
                        new ByteArrayInputStream(bytes)
                );

        Object deserialized = in.readObject();

        assertNotNull(deserialized);
        assertTrue(deserialized instanceof QuantityMeasurementEntity);
    }
}
