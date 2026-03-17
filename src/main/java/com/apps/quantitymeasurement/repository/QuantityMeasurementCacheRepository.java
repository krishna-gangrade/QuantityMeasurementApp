
package com.apps.quantitymeasurement.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementCacheRepository.class.getName());

    private static QuantityMeasurementCacheRepository instance;

    private List<QuantityMeasurementEntity> cache;

    /**
     * Private constructor for Singleton
     */
    private QuantityMeasurementCacheRepository() {
        cache = new ArrayList<>();
        logger.info("QuantityMeasurementCacheRepository initialized with empty cache");
    }

    /**
     * Get singleton instance
     */
    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            logger.info("Creating new instance of QuantityMeasurementCacheRepository");
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {

        if (entity == null) {
            logger.severe("Attempted to save a null entity");
            throw new IllegalArgumentException("Entity cannot be null");
        }

        cache.add(entity);
        logger.info("Entity saved to cache. Current cache size: " + cache.size());
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        logger.fine("Fetching all measurements from cache. Total: " + cache.size());
        return new ArrayList<>(cache);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {

        List<QuantityMeasurementEntity> result = new ArrayList<>();

        for (QuantityMeasurementEntity entity : cache) {
            if (entity.getOperation() != null &&
                entity.getOperation().equalsIgnoreCase(operation)) {
                result.add(entity);
            }
        }

        logger.info("Found " + result.size() + " measurements for operation: " + operation);
        return result;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {

        List<QuantityMeasurementEntity> result = new ArrayList<>();

        for (QuantityMeasurementEntity entity : cache) {
            if (entity.getThisUnit() != null &&
                entity.getThisUnit().equalsIgnoreCase(measurementType)) {
                result.add(entity);
            }
        }

        logger.info("Found " + result.size() + " measurements for type: " + measurementType);
        return result;
    }

    @Override
    public int getTotalCount() {
        logger.info("Total measurements in cache: " + cache.size());
        return cache.size();
    }

    @Override
    public void deleteAll() {
        logger.warning("Deleting all measurements from cache. Size before delete: " + cache.size());
        cache.clear();
    }

    @Override
    public String getPoolStatistics() {
        return "Cache repository does not use connection pool.";
    }

    @Override
    public void releaseResources() {
        logger.info("Cache repository has no resources to release.");
    }
    public void clear() {
        cache.clear();
    }
}
