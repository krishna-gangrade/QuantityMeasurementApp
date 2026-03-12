package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityMeasurementCacheRepository repo =
                QuantityMeasurementCacheRepository.getInstance();

        QuantityMeasurementServiceImpl service =
                new QuantityMeasurementServiceImpl(repo);

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "Length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "Length");

        boolean equal = controller.performComparison(q1, q2);

        System.out.println("Are Equal: " + equal);

        QuantityDTO sum = controller.performAddition(q1, q2);

        System.out.println("Addition Result: " + sum);
    }
}
