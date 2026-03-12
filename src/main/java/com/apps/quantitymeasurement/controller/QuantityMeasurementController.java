package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public boolean performComparison(QuantityDTO q1, QuantityDTO q2) {
        return service.compare(q1, q2);
    }

    public QuantityDTO performConversion(QuantityDTO q, String targetUnit) {
        return service.convert(q, targetUnit);
    }

    public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2) {
        return service.add(q1, q2);
    }

    public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        return service.add(q1, q2, targetUnit);
    }

    public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2) {
        return service.subtract(q1, q2);
    }

    public double performDivision(QuantityDTO q1, QuantityDTO q2) {
        return service.divide(q1, q2);
    }
}
