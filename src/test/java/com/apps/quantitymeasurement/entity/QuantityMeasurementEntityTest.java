package com.apps.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.*;

import com.apps.quantitymeasurement.unit.LengthUnit;

public class QuantityMeasurementEntityTest {

    // =====================================
    // QuantityDTO Tests
    // =====================================

    @Test
    void testQuantityDTOConstructorAndGetters() {

        QuantityDTO dto = new QuantityDTO(10.5, "FEET", "Length");

        assertEquals(10.5, dto.getValue());
        assertEquals("FEET", dto.getUnit());
        assertEquals("Length", dto.getMeasurementType());
    }

    @Test
    void testQuantityDTODefaultConstructor() {

        QuantityDTO dto = new QuantityDTO();

        assertEquals(0.0, dto.getValue());
        assertNull(dto.getUnit());
        assertNull(dto.getMeasurementType());
    }

    @Test
    void testQuantityDTOToString() {

        QuantityDTO dto = new QuantityDTO(5.0, "LITRE", "Volume");

        assertEquals("5.0 LITRE", dto.toString());
    }

    @Test
    void testQuantityDTONegativeValue() {

        QuantityDTO dto = new QuantityDTO(-10, "FEET", "Length");

        assertEquals(-10, dto.getValue());
    }

    @Test
    void testQuantityDTOLargeValue() {

        QuantityDTO dto = new QuantityDTO(1e9, "GRAM", "Weight");

        assertEquals(1e9, dto.getValue());
    }

    @Test
    void testQuantityDTONullFields() {

        QuantityDTO dto = new QuantityDTO(5, null, null);

        assertNull(dto.getUnit());
        assertNull(dto.getMeasurementType());
    }

    // =====================================
    // QuantityMeasurementEntity Tests
    // =====================================

    @Test
    void testEntityNormalConstructor() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(10, 5, "FEET", "ADD", 15);

        assertNotNull(entity);
    }

    @Test
    void testEntityErrorConstructor() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity("ADD", "Invalid operation");

        assertNotNull(entity);
    }

    @Test
    void testEntityExtremeValues() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(1e9, 1e9, "GRAM", "ADD", 2e9);

        assertNotNull(entity);
    }

    @Test
    void testEntityNegativeValues() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(-10, -5, "FEET", "SUBTRACT", -5);

        assertNotNull(entity);
    }

    @Test
    void testEntityZeroValues() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(0, 0, "FEET", "ADD", 0);

        assertNotNull(entity);
    }

    @Test
    void testEntitySerialization() throws Exception {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(10, 5, "FEET", "ADD", 15);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);

        out.writeObject(entity);
        out.flush();

        byte[] bytes = bos.toByteArray();

        ObjectInputStream in =
                new ObjectInputStream(new ByteArrayInputStream(bytes));

        Object deserialized = in.readObject();

        assertNotNull(deserialized);
        assertTrue(deserialized instanceof QuantityMeasurementEntity);
    }

    // =====================================
    // QuantityModel Tests
    // =====================================

    @Test
    void testQuantityModelConstructorAndGetters() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(10, LengthUnit.FEET);

        assertEquals(10, model.getValue());
        assertEquals(LengthUnit.FEET, model.getUnit());
    }

    @Test
    void testQuantityModelNegativeValue() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(-5, LengthUnit.FEET);

        assertEquals(-5, model.getValue());
    }

    @Test
    void testQuantityModelZeroValue() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(0, LengthUnit.FEET);

        assertEquals(0, model.getValue());
    }

    @Test
    void testQuantityModelLargeValue() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(1e9, LengthUnit.FEET);

        assertEquals(1e9, model.getValue());
    }

    @Test
    void testQuantityModelNullUnit() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(10, null);

        assertNull(model.getUnit());
    }
}
