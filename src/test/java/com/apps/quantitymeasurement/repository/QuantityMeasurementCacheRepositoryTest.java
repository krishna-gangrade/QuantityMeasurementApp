package com.apps.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepositoryTest {

    private QuantityMeasurementCacheRepository repository;

    @BeforeEach
    void setup() {

        repository = QuantityMeasurementCacheRepository.getInstance();

        // clear cache between tests
        repository.getAllMeasurements();
        
        repository.clear();
    }

    // =========================
    // SINGLETON TESTS
    // =========================

    @Test
    void testSingletonInstance() {

        QuantityMeasurementCacheRepository repo1 =
                QuantityMeasurementCacheRepository.getInstance();

        QuantityMeasurementCacheRepository repo2 =
                QuantityMeasurementCacheRepository.getInstance();

        assertSame(repo1, repo2);
    }

    // =========================
    // SAVE TESTS
    // =========================

    @Test
    void testSaveSingleEntity() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(1,2,"FEET","ADD",3);

        repository.save(entity);

        assertEquals(1, repository.getAllMeasurements().size());
    }

    @Test
    void testSaveMultipleEntities() {

        repository.save(new QuantityMeasurementEntity(1,1,"FEET","ADD",2));
        repository.save(new QuantityMeasurementEntity(2,2,"FEET","ADD",4));
        repository.save(new QuantityMeasurementEntity(3,3,"FEET","ADD",6));

        assertEquals(3, repository.getAllMeasurements().size());
    }

    // =========================
    // RETRIEVAL TESTS
    // =========================

    @Test
    void testGetAllMeasurementsReturnsData() {

        repository.save(new QuantityMeasurementEntity(1,1,"FEET","ADD",2));

        List<QuantityMeasurementEntity> result =
                repository.getAllMeasurements();

        assertFalse(result.isEmpty());
    }

    @Test
    void testGetAllMeasurementsInitiallyEmpty() {

        List<QuantityMeasurementEntity> result =
                repository.getAllMeasurements();

        assertNotNull(result);
    }

    // =========================
    // DEFENSIVE COPY TEST
    // =========================

    @Test
    void testReturnedListIsDefensiveCopy() {

        repository.save(new QuantityMeasurementEntity(1,1,"FEET","ADD",2));

        List<QuantityMeasurementEntity> list1 =
                repository.getAllMeasurements();

        list1.clear();

        List<QuantityMeasurementEntity> list2 =
                repository.getAllMeasurements();

        assertEquals(1, list2.size());
    }

    // =========================
    // NULL VALIDATION
    // =========================

    @Test
    void testSaveNullEntityThrowsException() {

        assertThrows(IllegalArgumentException.class,
                () -> repository.save(null));
    }

    // =========================
    // EXTREME DATA TEST
    // =========================

    @Test
    void testRepositoryHandlesLargeDataVolume() {

        for (int i = 0; i < 10000; i++) {

            repository.save(
                    new QuantityMeasurementEntity(
                            i, i, "FEET", "ADD", i * 2
                    )
            );
        }

        assertEquals(10000,
                repository.getAllMeasurements().size());
    }

    // =========================
    // DATA INTEGRITY TEST
    // =========================

    @Test
    void testSavedEntityIntegrity() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(5,3,"FEET","SUBTRACT",2);

        repository.save(entity);

        QuantityMeasurementEntity stored =
                repository.getAllMeasurements().get(0);

        assertNotNull(stored);
    }

    // =========================
    // CONCURRENT ACCESS TEST
    // =========================

    @Test
    void testConcurrentSaveOperations() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            repository.save(new QuantityMeasurementEntity(1,1,"FEET","ADD",2));
        });

        Thread t2 = new Thread(() -> {
            repository.save(new QuantityMeasurementEntity(2,2,"FEET","ADD",4));
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertEquals(2, repository.getAllMeasurements().size());
    }

}
