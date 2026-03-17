INSERT INTO quantity_measurement
(
    this_value,
    this_unit,
    this_measurement_type,

    that_value,
    that_unit,
    that_measurement_type,

    operation,

    result_value,
    result_unit,
    result_measurement_type,

    result_string,
    is_error,
    error_message
)
VALUES
(
    1,
    'FEET',
    'Length',

    12,
    'INCHES',
    'Length',

    'COMPARE',

    1,
    'FEET',
    'Length',

    'true',
    false,
    NULL
);

INSERT INTO quantity_measurement
(
    this_value,
    this_unit,
    this_measurement_type,

    that_value,
    that_unit,
    that_measurement_type,

    operation,

    result_value,
    result_unit,
    result_measurement_type,

    result_string,
    is_error,
    error_message
)
VALUES
(
    1,
    'LITRE',
    'Volume',

    1000,
    'MILLILITRE',
    'Volume',

    'ADD',

    2,
    'LITRE',
    'Volume',

    '2 LITRE',
    false,
    NULL
);