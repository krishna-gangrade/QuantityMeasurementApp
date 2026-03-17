
package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.quantity.Quantity;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.unit.*;

import java.util.logging.Logger;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementServiceImpl initialized with repository: "
                + repository.getClass().getSimpleName());
    }

    // ============================
    // UNIT RESOLUTION
    // ============================

    private IMeasurable resolveUnit(String unitName) {

        try { return LengthUnit.valueOf(unitName); } catch (Exception ignored) {}
        try { return WeightUnit.valueOf(unitName); } catch (Exception ignored) {}
        try { return VolumeUnit.valueOf(unitName); } catch (Exception ignored) {}
        try { return TemperatureUnit.valueOf(unitName); } catch (Exception ignored) {}

        throw new QuantityMeasurementException("Invalid unit: " + unitName);
    }

    private Quantity<IMeasurable> toQuantity(QuantityDTO dto) {

        IMeasurable unit = resolveUnit(dto.getUnit());

        return new Quantity<>(dto.getValue(), unit);
    }

    private QuantityDTO toDTO(Quantity<IMeasurable> quantity) {

        return new QuantityDTO(
                quantity.getValue(),
                quantity.getUnit().getUnitName(),
                quantity.getUnit().getMeasurementType()
        );
    }

    // ============================
    // ENTITY BUILDER
    // ============================

    private QuantityMeasurementEntity buildEntity(
            QuantityDTO q1,
            QuantityDTO q2,
            String operation,
            Quantity<IMeasurable> result) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType());

        if (q2 != null) {
            entity.setThatValue(q2.getValue());
            entity.setThatUnit(q2.getUnit());
            entity.setThatMeasurementType(q2.getMeasurementType());
        }

        entity.setOperation(operation);

        if (result != null) {
            entity.setResultValue(result.getValue());
            entity.setResultUnit(result.getUnit().getUnitName());
            entity.setResultMeasurementType(result.getUnit().getMeasurementType());
            entity.setResultString(result.getValue() + " " + result.getUnit().getUnitName());
        }

        entity.setError(false);

        return entity;
    }

    // ============================
    // COMPARE
    // ============================

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        try {

            boolean result = toQuantity(q1).equals(toQuantity(q2));

            Quantity<IMeasurable> quantity =
                    result ? toQuantity(q1) : toQuantity(q2);

            repository.save(buildEntity(q1, q2, "COMPARE", quantity));

            return result;

        } catch (Exception e) {

            logger.severe("Comparison failed: " + e.getMessage());

            repository.save(new QuantityMeasurementEntity("COMPARE", e.getMessage()));

            throw new QuantityMeasurementException("Comparison failed", e);
        }
    }

    // ============================
    // CONVERT
    // ============================

    @Override
    public QuantityDTO convert(QuantityDTO quantity, String targetUnit) {

        try {

            Quantity<IMeasurable> q = toQuantity(quantity);

            IMeasurable target = resolveUnit(targetUnit);

            Quantity<IMeasurable> result = q.convertTo(target);

            repository.save(buildEntity(quantity, null, "CONVERT", result));

            return toDTO(result);

        } catch (Exception e) {

            logger.severe("Conversion failed: " + e.getMessage());

            repository.save(new QuantityMeasurementEntity("CONVERT", e.getMessage()));

            throw new QuantityMeasurementException("Conversion failed", e);
        }
    }

    // ============================
    // ADD
    // ============================

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        try {

            Quantity<IMeasurable> result =
                    toQuantity(q1).add(toQuantity(q2));

            repository.save(buildEntity(q1, q2, "ADD", result));

            return toDTO(result);

        } catch (Exception e) {

            logger.severe("Addition failed: " + e.getMessage());

            repository.save(new QuantityMeasurementEntity("ADD", e.getMessage()));

            throw new QuantityMeasurementException("Addition failed", e);
        }
    }

    // ============================
    // ADD WITH TARGET UNIT
    // ============================

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {

        try {

            Quantity<IMeasurable> result =
                    toQuantity(q1).add(toQuantity(q2), resolveUnit(targetUnit));

            repository.save(buildEntity(q1, q2, "ADD_TARGET", result));

            return toDTO(result);

        } catch (Exception e) {

            logger.severe("Addition with target failed: " + e.getMessage());

            repository.save(new QuantityMeasurementEntity("ADD_TARGET", e.getMessage()));

            throw new QuantityMeasurementException("Addition failed", e);
        }
    }

    // ============================
    // SUBTRACT
    // ============================

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {

        try {

            Quantity<IMeasurable> result =
                    toQuantity(q1).subtract(toQuantity(q2));

            repository.save(buildEntity(q1, q2, "SUBTRACT", result));

            return toDTO(result);

        } catch (Exception e) {

            logger.severe("Subtraction failed: " + e.getMessage());

            repository.save(new QuantityMeasurementEntity("SUBTRACT", e.getMessage()));

            throw new QuantityMeasurementException("Subtraction failed", e);
        }
    }

    // ============================
    // SUBTRACT WITH TARGET UNIT
    // ============================

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit) {

        try {

            Quantity<IMeasurable> result =
                    toQuantity(q1).subtract(toQuantity(q2), resolveUnit(targetUnit));

            repository.save(buildEntity(q1, q2, "SUBTRACT_TARGET", result));

            return toDTO(result);

        } catch (Exception e) {

            logger.severe("Subtraction with target failed: " + e.getMessage());

            repository.save(new QuantityMeasurementEntity("SUBTRACT_TARGET", e.getMessage()));

            throw new QuantityMeasurementException("Subtraction failed", e);
        }
    }

    // ============================
    // DIVIDE
    // ============================

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {

        try {

            double result = toQuantity(q1).divide(toQuantity(q2));

            Quantity<IMeasurable> quantity = toQuantity(q1);

            repository.save(buildEntity(q1, q2, "DIVIDE", quantity));

            return result;

        } catch (Exception e) {

            logger.severe("Division failed: " + e.getMessage());

            repository.save(new QuantityMeasurementEntity("DIVIDE", e.getMessage()));

            throw new QuantityMeasurementException("Division failed", e);
        }
    }
}
