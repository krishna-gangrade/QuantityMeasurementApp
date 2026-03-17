
package com.apps.quantitymeasurement.integrationTests;

import com.apps.quantitymeasurement.QuantityMeasurementApp;
import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementApp app;
    private QuantityMeasurementController controller;
    private IQuantityMeasurementRepository repository;

    private static final double EPSILON = 0.0001;

    @BeforeAll
    void setUpEnvironment() {

        System.setProperty("app.env", "test");

        app = QuantityMeasurementApp.getInstance();

        controller = app.getController();
        repository = app.getRepository();
    }

    @BeforeEach
    void clearDatabase() {

        repository.deleteAll();
    }

    @AfterEach
    void cleanup() {

        repository.deleteAll();
    }

    // ===============================
    // BASIC END-TO-END COMPARISON
    // ===============================

    @Test
    void testEndToEndLengthComparison() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "Length");

        boolean result = controller.performComparison(q1, q2);

        assertTrue(result);

        assertTrue(repository.getAllMeasurements().size() > 0);
    }

    // ===============================
    // EXTREME VALUE TEST
    // ===============================

    @Test
    void testExtremeLargeValuesAddition() {

        QuantityDTO q1 = new QuantityDTO(1e9, "GRAM", "Weight");
        QuantityDTO q2 = new QuantityDTO(1e9, "GRAM", "Weight");

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(2e9, result.getValue(), EPSILON);
    }

    // ===============================
    // ZERO VALUE EDGE CASE
    // ===============================

    @Test
    void testZeroValueAddition() {

        QuantityDTO q1 = new QuantityDTO(0, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(0, "LITRE", "Volume");

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(0, result.getValue(), EPSILON);
    }

    // ===============================
    // NEGATIVE VALUE EDGE CASE
    // ===============================

    @Test
    void testNegativeValues() {

        QuantityDTO q1 = new QuantityDTO(-10, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(-5, "FEET", "Length");

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(-15, result.getValue(), EPSILON);
    }

    // ===============================
    // TEMPERATURE CONVERSION TEST
    // ===============================

    @Test
    void testTemperatureConversionExtreme() {

        QuantityDTO celsius =
                new QuantityDTO(0, "CELSIUS", "Temperature");

        QuantityDTO result =
                controller.performConversion(celsius, "FAHRENHEIT");

        assertEquals(32, result.getValue(), EPSILON);
    }

    // ===============================
    // DIVISION EXTREME CASE
    // ===============================

    @Test
    void testDivisionExtremeCase() {

        QuantityDTO q1 = new QuantityDTO(1e9, "GRAM", "Weight");
        QuantityDTO q2 = new QuantityDTO(1e3, "GRAM", "Weight");

        double result = controller.performDivision(q1, q2);

        assertEquals(1e6, result, EPSILON);
    }

    // ===============================
    // DIVISION BY ZERO
    // ===============================

    @Test
    void testDivisionByZero() {

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(0, "FEET", "Length");

        assertThrows(Exception.class,
                () -> controller.performDivision(q1, q2));
    }

    // ===============================
    // INVALID UNIT TEST
    // ===============================

    @Test
    void testInvalidUnitHandling() {

        QuantityDTO q1 = new QuantityDTO(10, "INVALID_UNIT", "Length");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "Length");

        assertThrows(Exception.class,
                () -> controller.performComparison(q1, q2));
    }

    // ===============================
    // DATABASE PERSISTENCE CHECK
    // ===============================

    @Test
    void testRepositoryPersistenceAfterOperation() {

        QuantityDTO q1 = new QuantityDTO(5, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "Length");

        controller.performAddition(q1, q2);

        assertTrue(repository.getAllMeasurements().size() > 0);
    }

    // ===============================
    // LARGE DATASET TEST
    // ===============================

    @Test
    void testLargeDatasetOperations() {

        for (int i = 0; i < 1000; i++) {

            QuantityDTO q1 = new QuantityDTO(i, "FEET", "Length");
            QuantityDTO q2 = new QuantityDTO(i, "FEET", "Length");

            controller.performAddition(q1, q2);
        }

        assertEquals(1000, repository.getAllMeasurements().size());
    }
}
