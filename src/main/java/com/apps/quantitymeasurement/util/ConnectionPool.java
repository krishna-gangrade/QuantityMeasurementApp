
package com.apps.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

public class ConnectionPool {

    private static final Logger logger =
            Logger.getLogger(ConnectionPool.class.getName());

    private static ConnectionPool instance;

    private List<Connection> availableConnections;

    private List<Connection> usedConnections;

    private final int poolSize;

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;
    private final String driverClass;
    private final String testQuery;

    private ConnectionPool() throws SQLException {

        ApplicationConfig config =
                ApplicationConfig.getInstance();

        poolSize =
                config.getIntProperty("db.pool-size", 5);

        dbUrl = config.getProperty("db.url");
        dbUsername = config.getProperty("db.username");
        dbPassword = config.getProperty("db.password");
        driverClass = config.getProperty("db.driver");

        testQuery =
                config.getProperty(
                        "db.hikari.connection-test-query",
                        "SELECT 1"
                );

        availableConnections = new ArrayList<>();
        usedConnections = new ArrayList<>();

        initializeConnections();
    }

    public static synchronized ConnectionPool getInstance()
            throws SQLException {

        if (instance == null) {

            instance = new ConnectionPool();
        }

        return instance;
    }

    private void initializeConnections() throws SQLException {

        try {

            Class.forName(driverClass);

        } catch (ClassNotFoundException e) {

            throw new SQLException(
                    "Database driver not found",
                    e
            );
        }

        for (int i = 0; i < poolSize; i++) {

            availableConnections.add(createConnection());
        }
    }

    private Connection createConnection() throws SQLException {

        return DriverManager.getConnection(
                dbUrl,
                dbUsername,
                dbPassword
        );
    }

    public synchronized Connection getConnection()
            throws SQLException {

        if (availableConnections.isEmpty()) {

            if (usedConnections.size() < poolSize) {

                availableConnections.add(createConnection());

            } else {

                throw new SQLException(
                        "Maximum pool size reached"
                );
            }
        }

        Connection connection =
                availableConnections.remove(
                        availableConnections.size() - 1
                );

        usedConnections.add(connection);

        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {

        if (connection == null) {
            return;
        }

        usedConnections.remove(connection);

        availableConnections.add(connection);
    }

    public boolean validateConnection(Connection connection) {

        try (Statement stmt = connection.createStatement()) {

            stmt.execute(testQuery);

            return true;

        } catch (SQLException e) {

            logger.warning(
                    "Connection validation failed: "
                            + e.getMessage()
            );

            return false;
        }
    }

    public synchronized void closeAll() {

        availableConnections.forEach(conn -> {

            try {
                conn.close();
            } catch (SQLException ignored) {
            }

        });

        usedConnections.forEach(conn -> {

            try {
                conn.close();
            } catch (SQLException ignored) {
            }

        });

        availableConnections.clear();
        usedConnections.clear();
    }

    public int getAvailableConnectionCount() {

        return availableConnections.size();
    }

    public int getUsedConnectionCount() {

        return usedConnections.size();
    }

    public int getTotalConnectionCount() {

        return availableConnections.size()
                + usedConnections.size();
    }
}
