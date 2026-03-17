# Quantity Measurement App – UC1 (Feet Equality)

### 📌 Overview

- This module checks whether two measurements given in feet are equal.
- It focuses on correct `object equality`, `safe floating-point comparison`, and clean OOP design.

### ⚙️ Use Case: UC1 – Feet Measurement Equality

- Accepts two numerical values in feet  
- Compares them for equality  
- Returns `true` if equal, otherwise `false`

### ⚙️ Key Implementation Points

- Uses a separate `Feet` class to represent a measurement  
- Measurement value is `private` and `final` (immutable)  
- `equals()` is overridden correctly  
- `Double.compare()` is used instead of `==`  
- Handles `null`, type mismatch, and same reference cases safely  

🔗 **Code Link:**   
[UC1: Feet measurement equality](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC1-FleetEquality)

---

# Quantity Measurement App – UC2 (Inches Equality)

### 📌 Overview

- This module checks whether two measurements given in **inches** are equal.
- It extends UC1 by supporting equality checks for inches while following the same design principles.

### ⚙️ Use Case: UC2 – Inches Measurement Equality

- Accepts two numerical values in inches  
- Compares them for equality  
- Returns `true` if equal, otherwise `false`

### ⚙️ Key Implementation Points

- Uses a separate `Inches` class to represent a measurement  
- Measurement value is `private` and `final` (immutable)  
- `equals()` is overridden correctly  
- `Double.compare()` is used instead of `==`  
- Handles `null`, type mismatch, and same reference cases safely  

🔗 **Code Link:**  
[UC2: Feet and Inches measurement equality](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC2-InchEquality)

---

# Quantity Measurement App – UC3 (Generic Quantity Length)

### 📌 Overview

- This module refactors Feet and Inches into a **single generic Length class**.
- It eliminates code duplication by applying the **DRY principle**.
- Supports equality comparison **across different units** (feet ↔ inches).

### ⚙️ Use Case: UC3 – Generic Quantity Length Equality

- Accepts two numerical values along with their respective unit types  
- Converts different units to a **common base unit**  
- Compares values for equality  
- Returns `true` if equivalent, otherwise `false`

### ⚙️ Key Implementation Points

- Uses a **single QuantityLength class**  
- Introduces a `LengthUnit enum` for supported units and conversion factors  
- Eliminates separate Feet and Inches classes  
- Conversion logic is centralised  
- `equals()` supports cross-unit comparison  
- Safe floating-point comparison used  

🔗 **Code Link:**  
[UC3: Generic Quantity Class for DRY Principle](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC3-GenericLength)

# Quantity Measurement App – UC4 (Extended Unit Support)

### 📌 Overview

- Extends the generic Length class by adding support for **Yards and Centimeters**.
- Demonstrates scalability without modifying core logic.

### ⚙️ Use Case: UC4 – Extended Quantity Length Equality

- Accepts two numerical values with supported units  
- Supports `feet`, `inches`, `yards`, `centimeters`  
- Converts to common base unit  
- Compares for equality  

### ⚙️ Key Implementation Points

- Extends existing `LengthUnit enum`  
- No change required in core logic  
- Conversion remains centralised  
- Cross-unit equality works seamlessly  

🔗 **Code Link:**  
[UC4: Extended Unit Support](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC4-YardEquality)

---

# Quantity Measurement App – UC5 (Unit-to-Unit Conversion)

### 📌 Overview

- Adds explicit **unit conversion API** to the system.
- Supports conversion across all length units.

### ⚙️ Use Case: UC5 – Unit-to-Unit Conversion

- Accepts value + source unit + target unit  
- Converts via common base unit  
- Returns converted value  

### ⚙️ Key Implementation Points

- Static method:  
  `static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit)`
- Validates units and finite values  
- Uses base unit normalisation  
- Maintains floating-point precision tolerance  
- Throws `IllegalArgumentException` for invalid inputs  

🔗 **Code Link:**  
[UC5: Unit-to-Unit Conversion](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC5-UnitConversion)

---

# Quantity Measurement App – UC6 (Addition of Two Length Units)

### 📌 Overview

- Enables addition between two length measurements.
- Supports cross-unit addition and returns result in first operand’s unit.

### ⚙️ Use Case: UC6 – Addition of Two Length Units

- Accepts two numerical values with their respective units  
- Adds them and returns sum in first operand’s unit  

### ⚙️ Key Implementation Points

- Converts operands to common base unit  
- Adds normalised values  
- Converts result back to first operand’s unit  
- Returns new immutable object  
- Validates null and invalid inputs  

🔗 **Code Link:**  
[UC6: Addition of Two Length Units](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC6-UnitAddition)

---

# Quantity Measurement App – UC7 (Addition with Target Unit Specification)

### 📌 Overview

- Extends UC6 by allowing explicit target unit specification.

### ⚙️ Use Case: UC7 – Addition with Target Unit Specification

- Accepts two values + units + target unit  
- Returns result in explicitly specified unit  

### ⚙️ Key Implementation Points

- Overloaded `add()` method  
- Converts → Adds → Converts to target  
- Validates target unit  
- Preserves immutability  

🔗 **Code Link:**  
[UC7: Addition with Target Unit Specification](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC7-TargetUnitAddition)

---

# Quantity Measurement App – UC8 (Standalone LengthUnit Refactoring)

### 📌 Overview

- Refactors `LengthUnit` into standalone top-level class.
- Delegates conversion responsibility fully to `LengthUnit`.

### ⚙️ Use Case: UC8 – Standalone Unit Refactoring

- LengthUnit manages conversion  
- QuantityLength handles equality and arithmetic  
- Backward compatibility maintained  

### ⚙️ Key Implementation Points

- `convertToBaseUnit(double value)`  
- `convertFromBaseUnit(double baseValue)`  
- Clean separation of responsibilities  
- Scalable architecture  

🔗 **Code Link:**  
[UC8: Refactoring Unit Enum to Standalone](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC8-StandAloneUnit)

---

# Quantity Measurement App – UC9 (Weight Equality, Conversion, and Addition)

### 📌 Overview

- Introduces support for weight measurements (`kg`, `g`, `lb`).

### ⚙️ Use Case: UC9 – Weight Measurement

- Equality comparison  
- Unit conversion  
- Addition operations  

### ⚙️ Key Implementation Points

- `WeightUnit enum` (base unit: kilogram)  
- `QuantityWeight class`  
- Cross-unit equality  
- Conversion via base unit  
- Immutable design  

🔗 **Code Link:**  
[UC9: Weight Measurement Equality, Conversion, and Addition (Kilogram, Gram, Pound)](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC9-WeightMeasurement)

---

# Quantity Measurement App – UC10 (Generic Quantity Class with Unit Interface)

### 📌 Overview

- This module refactors the previous category-specific Quantity classes into a single, generic `Quantity<U>` class that works with any measurement category implementing the `IMeasurable` interface.
- It eliminates code duplication, simplifies demonstration methods, and ensures type-safe operations across multiple measurement categories like length and weight.

### ⚙️ Use Case: UC10 – Generic Quantity and Multi-Category Support

- Accepts two numerical values with their respective units
- Supports equality comparison, unit conversion, and addition
- Prevents invalid cross-category comparisons (e.g., length vs. weight)
- Returns a new `Quantity` object for conversion or addition; equality returns a boolean

### ⚙️ Key Implementation Points

- Uses a single generic class: `Quantity<U extends IMeasurable>`
- Holds private final fields: `value` and `unit` (immutable)
- `IMeasurable` interface standardises unit behaviour across categories
- Enums (`LengthUnit`, `WeightUnit`) implement `IMeasurable` and encapsulate conversion logic
- `equals()` compares base unit values using `Double.compare()` and validates unit types
- `convertTo(U targetUnit)` delegates to the unit’s conversion methods and returns new instance
- `add(Quantity<U> other)` and `add(Quantity<U> other, U targetUnit)` perform arithmetic safely
- `hashCode()` and `toString()` overridden for collections and readable output
- Type safety ensured at compile-time via generics; runtime unit class checks prevent cross-category errors
- Demonstration methods in `QuantityMeasurementApp` are generic and unified for all categories
- 
🔗 **Code Link:**  
[UC10: Generic Quantity Class with Unit Interface for Multi-Category Support](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC10-GenericQuality)

---

# Quantity Measurement App – UC11 (Volume Equality, Conversion, and Addition)

### 📌 Overview

- This module extends the Quantity Measurement Application to support **volume measurements** (litres, millilitres, gallons).
- It demonstrates equality comparison, unit conversion, and addition operations for volume, leveraging the generic `Quantity<U>` class and `IMeasurable` interface. - Volume is treated as a separate category from length and weight, validating the scalability of the generic architecture.

### ⚙️ Use Case:  UC11 – Volume Measurement Equality, Conversion, and Addition

- Accepts numerical values with their respective volume units (LITRE, MILLILITRE, GALLON)
- Compares volumes for equality
- Converts between volume units
- Adds two volume quantities, optionally specifying a target unit

### ⚙️ Key Implementation Points

- `VolumeUnit` enum implements `IMeasurable` with LITRE as the base unit
- Conversion factors: MILLILITRE = 0.001 L, GALLON ≈ 3.78541 L
- Equality uses base unit comparison with epsilon tolerance
- Generic `Quantity<U>` handles conversion and addition without modification
- Maintains type safety: volume cannot be mixed with length or weight
- Objects are immutable; addition and conversion return new instances

🔗 **Code Link:**  
[UC11: Volume Measurement Equality, Conversion, and Addition (Litre, Millilitre, Gallon)](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC11-VolumeEquality)

---

# Quantity Measurement App - UC13 (Centralised Arithmetic Logic to Enforce DRY in Quantity Operations)

### 📌 Overview

- UC13 refactors the arithmetic operations introduced in UC12 by centralising all shared validation, unit conversion, and base-unit arithmetic logic into private helper methods.
- This refactoring enforces the DRY (Don’t Repeat Yourself) principle, reduces code duplication, and improves maintainability, while keeping all public APIs and behaviours unchanged.

### ⚙️ Use Case: UC13 Centralised Arithmetic Logic

- Eliminate repeated logic across the add, subtract, and divide methods
- Ensure consistent validation and error handling for all arithmetic operations
- Improve readability and maintainability of arithmetic logic
- Provide a scalable foundation for future operations (multiply, modulo, etc.)
- Preserve all UC12 behaviour and existing test cases

### ⚙️ Key Implementation Points (Brief)

- Centralised validation logic in one private helper method.
- Single helper for base-unit conversion and arithmetic.
- `ArithmeticOperation` enum (ADD, SUBTRACT, DIVIDE) encapsulates operation logic.
- `add`, `subtract`, `divide` delegate to shared helpers.
- Implicit and explicit target unit behaviour preserved.
- Public APIs unchanged; UC12 tests pass as-is.
- DRY enforced, cleaner code, easier future extension.

🔗 **Code Link:**  
[UC13: Centralized Arithmetic Logic to Enforce DRY in Quantity Operations](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC13-ArithematicOperation)

---

# Quantity Measurement App - UC12 (Subtraction and Division Operations on Quantity Measurements)

### 📌 Overview

- UC12 extends the Quantity Measurement Application by `adding subtraction` and `division operations` to the `generic Quantity<U> model`.
- It builds on `UC1–UC11` and enables full arithmetic manipulation while preserving immutability, type safety, and cross-unit support.

### ⚙️ Use Case: UC12 – Quantity Subtraction & Division

- Subtract two quantities of the same measurement category
- Divide two quantities to obtain a dimensionless ratio
- Support `cross-unit` arithmetic (e.g., feet ↔ inches, litre ↔ millilitre)
- Prevent `cross-category` operations (e.g., length vs weight)

### ⚙️Key Implementation Points

 - Convert operands to base unit before arithmetic
- Validate:
    - Null operands
    - Same measurement category
    - Finite numeric values
    - Division by zero
- Implicit target unit → first operand’s unit
- Explicit target unit supported
- Results rounded to two decimal places (subtraction only)

🔗 **Code Link:**  
[UC12: Subtraction and Division Operations on Quantity Measurements](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC12-SubtractionDivision)

---

# Quantity Measurement App - UC13 (Centralised Arithmetic Logic to Enforce DRY in Quantity Operations)

### 📌 Overview

- UC13 refactors the arithmetic operations introduced in UC12 by centralising all shared validation, unit conversion, and base-unit arithmetic logic into private helper methods.
- This refactoring enforces the DRY (Don’t Repeat Yourself) principle, reduces code duplication, and improves maintainability, while keeping all public APIs and behaviours unchanged.

### ⚙️ Use Case: UC13 Centralised Arithmetic Logic

- Eliminate repeated logic across the add, subtract, and divide methods
- Ensure consistent validation and error handling for all arithmetic operations
- Improve readability and maintainability of arithmetic logic
- Provide a scalable foundation for future operations (multiply, modulo, etc.)
- Preserve all UC12 behaviour and existing test cases

### ⚙️ Key Implementation Points (Brief)

- Centralised validation logic in one private helper method.
- Single helper for base-unit conversion and arithmetic.
- `ArithmeticOperation` enum (ADD, SUBTRACT, DIVIDE) encapsulates operation logic.
- `add`, `subtract`, `divide` delegate to shared helpers.
- Implicit and explicit target unit behaviour preserved.
- Public APIs unchanged; UC12 tests pass as-is.
- DRY enforced, cleaner code, easier future extension.

🔗 **Code Link:**  
[UC13: Centralized Arithmetic Logic to Enforce DRY in Quantity Operations](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC13-ArithematicOperation)

---

# Quantity Measurement App - UC14 (Temperature Measurement with Selective Arithmetic Support)

### 📌 Overview

- UC14 extends the Quantity Measurement Application to support temperature measurements while respecting real-world arithmetic constraints.
- Unlike length, weight, and volume, temperature does not support full arithmetic.
- This use case refactors the IMeasurable interface to make arithmetic optional, enabling temperature units to support only equality and conversion while rejecting unsupported operations with clear errors.

### ⚙️ Use Case: UC14 (Temperature Measurement)

- Introduces **temperature measurement support** with unit conversion and equality
- Restricts **unsupported arithmetic operations** on temperature with clear validation
- Refactors `IMeasurable` to allow **selective operation support** while keeping existing units unchanged

### ⚙️ UC14 – Key Implementation Points

* Introduced `TemperatureUnit` (Celsius, Fahrenheit, Kelvin) with non-linear conversion formulas.
* Refactored `IMeasurable` to add `default methods` for optional arithmetic support.
* Added **SupportsArithmetic** functional interface with lambda-based capability flags.
* Non-temperature units inherit default arithmetic support (**backwards compatible**).
* Temperature explicitly **disables arithmetic** (add, subtract, divide) via overrides.
* `Quantity` validates `operation support upfront` before performing arithmetic.
* Equality and conversion work uniformly via **base-unit normalisation**.
* Cross-category comparisons remain `prohibited and type-safe`.
* Unsupported operations fail fast with **clear UnsupportedOperationException** messages.
* All **UC1–UC13 tests pass unchanged**, ensuring non-breaking evolution.

🔗 **Code Link:**  
[UC14: Temperature Measurement with Selective Arithmetic Support and IMeasurable Refactoring](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC14-TemperatureManagement)

---

# Quantity Measurement App – UC15 (N-Tier Architecture Refactoring)

### 📌 Overview

- Refactors the Quantity Measurement Application from a **monolithic structure** into a **clean N-Tier architecture**.
- Separates the system into **Controller, Service, Repository, and Entity layers**.
- Improves **maintainability, scalability, and testability** while preserving all functionality from **UC1–UC14**.

### ⚙️ Use Case: UC15 – N-Tier Architecture

- Introduces layered architecture for clear separation of concerns  
- Moves business logic to the **Service layer**  
- Uses **DTO and Model classes** for structured data flow  
- Stores operation history using a **Repository layer**

### ⚙️ Key Implementation Points

- **Application Layer** → `QuantityMeasurementApp` initializes components and triggers operations  
- **Controller Layer** → `QuantityMeasurementController` handles requests and delegates to service  
- **Service Layer** → `QuantityMeasurementServiceImpl` contains core business logic  
- **Repository Layer** → `QuantityMeasurementCacheRepository` manages operation storage  
- **Entity / Model Layer** → `QuantityDTO`, `QuantityModel`, `QuantityMeasurementEntity`

- Uses design patterns:
  - **Singleton Pattern** (Repository)
  - **Factory Pattern** (Object creation)
  - **Facade Pattern** (Controller interface)
  - **Dependency Injection**

🔗 **Code Link:**  
[UC15: N-Tier Architecture Refactoring](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC15-N-Tier)

---

# Quantity Measurement App – UC16 (Database Integration with JDBC)

### 📌 Overview

- This module enhances the application by adding **database persistence using JDBC**.
- Replaces in-memory storage with a **database-backed repository**.
- Enables **audit trails, historical data storage, and scalable persistence**.

### ⚙️ Use Case: UC16 – Database Persistence with JDBC

- Stores quantity measurement operations in a relational database  
- Retrieves historical measurement data  
- Supports switching between cache and database repositories  
- Enables persistent storage across application restarts  

### ⚙️ Key Implementation Points

- Introduces `QuantityMeasurementDatabaseRepository` using **JDBC**  
- Uses **connection pooling** for efficient DB access  
- Implements **parameterized SQL queries** (prevents SQL injection)  
- Adds **transaction management** for consistency  
- Database schema managed via `schema.sql`  
- Configuration handled via `application.properties`  
- Supports **H2 (default), MySQL, PostgreSQL** (configurable)  
- Follows **Maven standard project structure**  
- Adds dependencies: JDBC, H2, SLF4J, Logback, Mockito, JUnit  
- Separates **test and production databases**  
- Maintains backward compatibility with UC15 via interface-based repository  
- Improves scalability, concurrency handling, and query capabilities over cache  

### ⚙️ Key Improvements Over UC15

- Persistent storage (no data loss on restart)  
- Supports concurrent access and transactions  
- Enables SQL-based querying and reporting  
- Better debugging, monitoring, and analytics support  
- Scales beyond memory limitations  

🔗 **Code Link:**  
[UC16: Database Integration with JDBC](https://github.com/krishna-gangrade/QuantityMeasurementApp/tree/feature/UC16-DatabaseIntegration)

---
