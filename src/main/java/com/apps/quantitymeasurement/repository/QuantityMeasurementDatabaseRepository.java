
package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.DatabaseException;
import com.apps.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());

    private static QuantityMeasurementDatabaseRepository instance;

    private ConnectionPool connectionPool;

    private static final String INSERT_QUERY =
            "INSERT INTO quantity_measurement_entity " +
            "(this_value, this_unit, this_measurement_type, that_value, that_unit, " +
            "that_measurement_type, operation, result_value, result_unit, " +
            "result_measurement_type, result_string, is_error, error_message, created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
    
    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM quantity_measurement_entity ORDER BY created_at DESC";

    private static final String SELECT_BY_OPERATION =
            "SELECT * FROM quantity_measurement_entity WHERE operation = ? ORDER BY created_at DESC";

    private static final String SELECT_BY_TYPE =
            "SELECT * FROM quantity_measurement_entity WHERE this_measurement_type = ? ORDER BY created_at DESC";

    private static final String DELETE_ALL_QUERY =
            "DELETE FROM quantity_measurement_entity";

    private static final String COUNT_QUERY =
            "SELECT COUNT(*) FROM quantity_measurement_entity";

    private QuantityMeasurementDatabaseRepository() {

        try {

            connectionPool = ConnectionPool.getInstance();

            initializeDatabase();

        } catch (Exception e) {

            throw new DatabaseException("Failed to initialize database repository", e);
        }
    }

    public static synchronized QuantityMeasurementDatabaseRepository getInstance() {

        if (instance == null) {

            instance = new QuantityMeasurementDatabaseRepository();
        }

        return instance;
    }

    private void initializeDatabase() {

        String createTableQuery =
                "CREATE TABLE IF NOT EXISTS quantity_measurement_entity (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "this_value DOUBLE," +
                        "this_unit VARCHAR(50)," +
                        "this_measurement_type VARCHAR(50)," +
                        "that_value DOUBLE," +
                        "that_unit VARCHAR(50)," +
                        "that_measurement_type VARCHAR(50)," +
                        "operation VARCHAR(50)," +
                        "result_value DOUBLE," +
                        "result_unit VARCHAR(50)," +
                        "result_measurement_type VARCHAR(50)," +
                        "result_string VARCHAR(255)," +
                        "is_error BOOLEAN," +
                        "error_message VARCHAR(255)," +
                        "created_at TIMESTAMP," +
                        "updated_at TIMESTAMP" +
                        ")";

        try (Connection conn = connectionPool.getConnection();
        	     Statement stmt = conn.createStatement()) {

        	    stmt.execute(createTableQuery);

        	} catch (SQLException e) {
        	    throw DatabaseException.queryFailed(createTableQuery, e);
        	}
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            conn = connectionPool.getConnection();

            stmt = conn.prepareStatement(INSERT_QUERY);

            stmt.setDouble(1, entity.getThisValue());
            stmt.setString(2, entity.getThisUnit());
            stmt.setString(3, entity.getThisMeasurementType());
            stmt.setDouble(4, entity.getThatValue());
            stmt.setString(5, entity.getThatUnit());
            stmt.setString(6, entity.getThatMeasurementType());
            stmt.setString(7, entity.getOperation());
            stmt.setDouble(8, entity.getResultValue());
            stmt.setString(9, entity.getResultUnit());
            stmt.setString(10, entity.getResultMeasurementType());
            stmt.setString(11, entity.getResultString());
            stmt.setBoolean(12, entity.isError());
            stmt.setString(13, entity.getErrorMessage());

            stmt.executeUpdate();

        } catch (SQLException e) {

            throw DatabaseException.queryFailed("INSERT measurement", e);

        } finally {

            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}

            if (conn != null) {
                connectionPool.releaseConnection(conn);
            }
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        try {

            Connection conn = connectionPool.getConnection();

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);

            while (rs.next()) {

                list.add(mapResultSetToEntity(rs));
            }

            closeResources(rs, stmt, conn);

        } catch (SQLException e) {

            throw DatabaseException.queryFailed("SELECT ALL", e);
        }

        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        try {

            Connection conn = connectionPool.getConnection();

            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_OPERATION);

            stmt.setString(1, operation);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                list.add(mapResultSetToEntity(rs));
            }

            closeResources(rs, stmt, conn);

        } catch (SQLException e) {

            throw DatabaseException.queryFailed("SELECT BY OPERATION", e);
        }

        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        try {

            Connection conn = connectionPool.getConnection();

            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_TYPE);

            stmt.setString(1, measurementType);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                list.add(mapResultSetToEntity(rs));
            }

            closeResources(rs, stmt, conn);

        } catch (SQLException e) {

            throw DatabaseException.queryFailed("SELECT BY TYPE", e);
        }

        return list;
    }

    @Override
    public int getTotalCount() {

        try {

            Connection conn = connectionPool.getConnection();

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(COUNT_QUERY);

            rs.next();

            int count = rs.getInt(1);

            closeResources(rs, stmt, conn);

            return count;

        } catch (SQLException e) {

            throw DatabaseException.queryFailed("COUNT QUERY", e);
        }
    }

    @Override
    public void deleteAll() {

        try {

            Connection conn = connectionPool.getConnection();

            Statement stmt = conn.createStatement();

            stmt.executeUpdate(DELETE_ALL_QUERY);

            closeResources(stmt, conn);

        } catch (SQLException e) {

            throw DatabaseException.queryFailed("DELETE ALL", e);
        }
    }

    @Override
    public String getPoolStatistics() {

        return "Available connections: " +
                connectionPool.getAvailableConnectionCount() +
                ", Used connections: " +
                connectionPool.getUsedConnectionCount() +
                ", Total connections: " +
                connectionPool.getTotalConnectionCount();
    }

    @Override
    public void releaseResources() {

        connectionPool.closeAll();
    }

    private QuantityMeasurementEntity mapResultSetToEntity(ResultSet rs) throws SQLException {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(rs.getDouble("this_value"));
        entity.setThisUnit(rs.getString("this_unit"));
        entity.setThisMeasurementType(rs.getString("this_measurement_type"));

        entity.setThatValue(rs.getDouble("that_value"));
        entity.setThatUnit(rs.getString("that_unit"));
        entity.setThatMeasurementType(rs.getString("that_measurement_type"));

        entity.setOperation(rs.getString("operation"));

        entity.setResultValue(rs.getDouble("result_value"));
        entity.setResultUnit(rs.getString("result_unit"));
        entity.setResultMeasurementType(rs.getString("result_measurement_type"));

        entity.setResultString(rs.getString("result_string"));

        entity.setError(rs.getBoolean("is_error"));
        entity.setErrorMessage(rs.getString("error_message"));

        return entity;
    }

    private void closeResources(ResultSet rs, Statement stmt, Connection conn) {

        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
        try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
        try { if (conn != null) connectionPool.releaseConnection(conn); } catch (Exception ignored) {}
    }

    private void closeResources(Statement stmt, Connection conn) {

        try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
        try { if (conn != null) connectionPool.releaseConnection(conn); } catch (Exception ignored) {}
    }
}
