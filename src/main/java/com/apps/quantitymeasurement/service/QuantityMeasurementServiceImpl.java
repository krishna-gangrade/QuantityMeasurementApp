package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.quantity.Quantity;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.unit.*;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable resolveUnit(String unitName) {

        try { return LengthUnit.valueOf(unitName); }
        catch (Exception ignored) {}

        try { return WeightUnit.valueOf(unitName); }
        catch (Exception ignored) {}

        try { return VolumeUnit.valueOf(unitName); }
        catch (Exception ignored) {}

        try { return TemperatureUnit.valueOf(unitName); }
        catch (Exception ignored) {}

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
                quantity.getUnit().getClass().getSimpleName());
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        try {

            boolean result = toQuantity(q1).equals(toQuantity(q2));

            repository.save(new QuantityMeasurementEntity(
                    q1.getValue(),
                    q2.getValue(),
                    q1.getUnit(),
                    "COMPARE",
                    result ? 1 : 0
            ));

            return result;

        } catch (Exception e) {

            repository.save(new QuantityMeasurementEntity("COMPARE", e.getMessage()));

            throw new QuantityMeasurementException("Comparison failed", e);
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO quantity, String targetUnit) {

        try {

            Quantity<IMeasurable> q = toQuantity(quantity);

            IMeasurable target = resolveUnit(targetUnit);

            Quantity<IMeasurable> result = q.convertTo(target);

            repository.save(new QuantityMeasurementEntity(
                    quantity.getValue(),
                    result.getValue(),
                    quantity.getUnit(),
                    "CONVERT",
                    result.getValue()
            ));

            return toDTO(result);

        } catch (Exception e) {

            repository.save(new QuantityMeasurementEntity("CONVERT", e.getMessage()));

            throw new QuantityMeasurementException("Conversion failed", e);
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        try {

            Quantity<IMeasurable> result = toQuantity(q1).add(toQuantity(q2));

            repository.save(new QuantityMeasurementEntity(
                    q1.getValue(),
                    q2.getValue(),
                    q1.getUnit(),
                    "ADD",
                    result.getValue()
            ));

            return toDTO(result);

        } catch (Exception e) {

            repository.save(new QuantityMeasurementEntity("ADD", e.getMessage()));

            throw new QuantityMeasurementException("Addition failed", e);
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {

        Quantity<IMeasurable> result =
                toQuantity(q1).add(toQuantity(q2), resolveUnit(targetUnit));

        return toDTO(result);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {

        Quantity<IMeasurable> result =
                toQuantity(q1).subtract(toQuantity(q2));

        return toDTO(result);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit) {

        Quantity<IMeasurable> result =
                toQuantity(q1).subtract(toQuantity(q2), resolveUnit(targetUnit));

        return toDTO(result);
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {

        return toQuantity(q1).divide(toQuantity(q2));
    }
}
