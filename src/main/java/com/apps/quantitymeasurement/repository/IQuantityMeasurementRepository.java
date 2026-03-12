package com.apps.quantitymeasurement.repository;

import java.util.List;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {

    /**
     * Save a measurement entity
     */
    void save(QuantityMeasurementEntity entity);

    /**
     * Retrieve all measurements
     */
    List<QuantityMeasurementEntity> getAllMeasurements();
    
    
}
