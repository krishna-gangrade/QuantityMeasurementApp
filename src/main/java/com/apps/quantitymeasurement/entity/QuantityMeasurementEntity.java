package com.apps .quantitymeasurement.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private double thisValue;
    private double thatValue;
    private String unit;
    private String operation;
    private double result;
    private boolean isError;
    private String errorMessage;

    public QuantityMeasurementEntity(double thisValue, double thatValue,
                                     String unit, String operation, double result) {
        this.thisValue = thisValue;
        this.thatValue = thatValue;
        this.unit = unit;
        this.operation = operation;
        this.result = result;
        this.isError = false;
    }

    public QuantityMeasurementEntity(String operation, String errorMessage) {
        this.operation = operation;
        this.errorMessage = errorMessage;
        this.isError = true;
    }
}
