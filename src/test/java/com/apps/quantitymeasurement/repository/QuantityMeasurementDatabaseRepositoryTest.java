
package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementDatabaseRepositoryTest {

    private QuantityMeasurementDatabaseRepository repository;
    private QuantityMeasurementEntity testEntity;

    @BeforeAll
    static void setUpDatabase() {
        System.setProperty("app.env", "test");
    }

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementDatabaseRepository.getInstance();
        repository.deleteAll();
        createTestEntity();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testSaveEntity() {

        repository.save(testEntity);

        List<QuantityMeasurementEntity> result =
                repository.getAllMeasurements();

        assertEquals(1, result.size());
    }

    @Test
    void testGetAllMeasurements() {

        repository.save(testEntity);
        repository.save(createTestEntityCopy(5.0));

        List<QuantityMeasurementEntity> result =
                repository.getAllMeasurements();

        assertEquals(2, result.size());
    }

    @Test
    void testGetMeasurementsByOperation() {

        repository.save(testEntity);

        repository.save(new QuantityMeasurementEntity(
                5.0, "FEET",
                5.0, "FEET",
                "SUBTRACT",
                0.0
        ));

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByOperation("ADD");

        assertFalse(result.isEmpty());
        assertEquals("ADD", result.get(0).getOperation());
    }

    @Test
    void testGetMeasurementsByType() {

        repository.save(testEntity);

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByType("LENGTH");

        assertNotNull(result);
    }

    @Test
    void testGetTotalCount() {

        repository.save(testEntity);

        int count = repository.getTotalCount();

        assertEquals(1, count);
    }

    @Test
    void testDeleteAll() {

        repository.save(testEntity);

        repository.deleteAll();

        List<QuantityMeasurementEntity> result =
                repository.getAllMeasurements();

        assertEquals(0, result.size());
    }

    // Helper method to create base test entity
    private void createTestEntity() {

        testEntity = new QuantityMeasurementEntity(
                10.0, "FEET",
                5.0, "FEET",
                "ADD",
                15.0
        );
    }

    // Create a copy of test entity with different value
    private QuantityMeasurementEntity createTestEntityCopy(double value) {

        return new QuantityMeasurementEntity(
                value, "FEET",
                value, "FEET",
                "ADD",
                value + value
        );
    }
}
