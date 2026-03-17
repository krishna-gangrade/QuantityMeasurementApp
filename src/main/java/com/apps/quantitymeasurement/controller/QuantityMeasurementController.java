package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

import java.util.logging.Logger;

public class QuantityMeasurementController {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementController.class.getName());

    private IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {

        this.service = service;

        logger.info("QuantityMeasurementController initialized with service: "
                + service.getClass().getSimpleName());
    }

    public boolean performComparison(QuantityDTO q1, QuantityDTO q2) {

        logger.info("Performing comparison");

        return service.compare(q1, q2);
    }

    public QuantityDTO performConversion(QuantityDTO quantity, String targetUnit) {

        logger.info("Performing conversion");

        return service.convert(quantity, targetUnit);
    }

    public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2) {

        logger.info("Performing addition");

        return service.add(q1, q2);
    }
    
    public QuantityDTO performAddition(
            QuantityDTO q1,
            QuantityDTO q2,
            String targetUnit) {

        logger.info("Operation: ADD with target unit");

        return service.add(q1, q2, targetUnit);
    }
    
    public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2) {

        logger.info("Operation: SUBTRACT");

        return service.subtract(q1, q2);
    }
    
    public QuantityDTO performSubtraction(
            QuantityDTO q1,
            QuantityDTO q2,
            String targetUnit) {

        logger.info("Operation: SUBTRACT with target unit");

        return service.subtract(q1, q2, targetUnit);
    }

    public double performDivision(QuantityDTO q1, QuantityDTO q2) {

        logger.info("Performing division");

        return service.divide(q1, q2);
    }
}