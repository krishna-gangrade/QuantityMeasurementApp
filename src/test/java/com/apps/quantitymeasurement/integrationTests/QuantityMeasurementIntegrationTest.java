
package com.apps.quantitymeasurement.integrationTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementController controller;

    @BeforeEach
    void setup() {

        controller =
                new QuantityMeasurementController(
                        new QuantityMeasurementServiceImpl(
                                QuantityMeasurementCacheRepository.getInstance()));
    }

    @Test
    void additionTest() {

        QuantityDTO q1 = new QuantityDTO(1.0, "LITRE", "Volume");
        QuantityDTO q2 = new QuantityDTO(1000.0, "MILLILITRE", "Volume");

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(2.0, result.getValue());
    }
}
