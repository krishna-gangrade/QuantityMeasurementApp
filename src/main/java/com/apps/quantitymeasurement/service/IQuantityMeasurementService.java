package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.entity.QuantityDTO;

public interface IQuantityMeasurementService {

    boolean compare(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO convert(QuantityDTO quantity, String targetUnit);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    double divide(QuantityDTO q1, QuantityDTO q2);
}
