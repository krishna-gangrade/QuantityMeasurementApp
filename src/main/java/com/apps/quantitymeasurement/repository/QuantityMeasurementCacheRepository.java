package com.apps.quantitymeasurement.repository;

import java.util.ArrayList;
import java.util.List;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static QuantityMeasurementCacheRepository instance;

    private List<QuantityMeasurementEntity> cache;

    /**
     * Private constructor for Singleton
     */
    private QuantityMeasurementCacheRepository() {
        cache = new ArrayList<>();
    }

    /**
     * Get singleton instance
     */
    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        cache.add(entity);
    }
    
    public void clear() {
        cache.clear();
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return new ArrayList<>(cache);
    }
}
