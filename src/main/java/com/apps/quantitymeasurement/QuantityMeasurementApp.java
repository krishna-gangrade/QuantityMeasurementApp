
package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.util.ApplicationConfig;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Handler;

public class QuantityMeasurementApp {

	 private static QuantityMeasurementApp instance;
    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementApp.class.getName());

    private IQuantityMeasurementRepository repository;
    private QuantityMeasurementServiceImpl service;
    public QuantityMeasurementController controller;

    private QuantityMeasurementApp() {

        ApplicationConfig config = ApplicationConfig.getInstance();

        String repoType = config.getProperty("repository.type", "cache");

        if ("database".equalsIgnoreCase(repoType)) {
            repository = QuantityMeasurementDatabaseRepository.getInstance();
            logger.info("Using Database Repository");
        } else {
            repository = QuantityMeasurementCacheRepository.getInstance();
            logger.info("Using Cache Repository");
        }

        service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    public static void main(String[] args) {

        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.FINE);

        for (Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.FINE);
        }

        try {

            QuantityMeasurementApp app = new QuantityMeasurementApp();
            QuantityMeasurementController controller = app.controller;

            /* ================= COMPARISON ================= */

            QuantityDTO q1 = new QuantityDTO(
                    2,
                    QuantityDTO.LengthUnit.FEET.getUnitName(),
                    QuantityDTO.LengthUnit.FEET.getMeasurementType());

            QuantityDTO q2 = new QuantityDTO(
                    24,
                    QuantityDTO.LengthUnit.INCHES.getUnitName(),
                    QuantityDTO.LengthUnit.INCHES.getMeasurementType());

            boolean comparison = controller.performComparison(q1, q2);

            logger.info("Comparison result: " + comparison);


            /* ================= CONVERSION ================= */

            QuantityDTO converted =
                    controller.performConversion(
                            q1,
                            QuantityDTO.LengthUnit.INCHES.getUnitName());

            logger.info("Conversion result: " + converted);


            /* ================= ADDITION ================= */

            QuantityDTO addition =
                    controller.performAddition(q1, q2);

            logger.info("Result of addition: " + addition);


            /* ================= DIVISION ================= */

            double division =
                    controller.performDivision(q1, q2);

            logger.info("Result of division: " + division);


            /* ================= ADDITION WITH TARGET ================= */

            QuantityDTO addTarget =
                    controller.performAddition(
                            q1,
                            q2,
                            QuantityDTO.LengthUnit.CENTIMETERS.getUnitName());

            logger.info("Result of addition with target unit: " + addTarget);


            /* ================= TEMPERATURE ================= */

            QuantityDTO celsius =
                    new QuantityDTO(
                            4,
                            QuantityDTO.TemperatureUnit.CELSIUS.getUnitName(),
                            QuantityDTO.TemperatureUnit.CELSIUS.getMeasurementType());

            QuantityDTO fahrenheit =
                    controller.performConversion(
                            celsius,
                            QuantityDTO.TemperatureUnit.FAHRENHEIT.getUnitName());

            logger.info("Temperature conversion result: " + fahrenheit);


            /* ================= TEMPERATURE ERROR ================= */

            try {

                controller.performAddition(celsius, fahrenheit);

            } catch (Exception e) {

                logger.info("Temperature addition not supported: " + e.getMessage());
            }


            

        } catch (Exception e) {
            logger.severe("Application failed");
            e.printStackTrace();
        }
    }
    
    public static synchronized QuantityMeasurementApp getInstance() {

        if (instance == null) {
            instance = new QuantityMeasurementApp();
        }

        return instance;
    }

    public QuantityMeasurementController getController() {
        return controller;
    }

    public IQuantityMeasurementRepository getRepository() {
        return repository;
    }
}
