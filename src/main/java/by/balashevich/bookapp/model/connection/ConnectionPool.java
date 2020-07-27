package by.balashevich.bookapp.model.connection;

import by.balashevich.bookapp.exception.ConnectionDatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "Java1234";
    private static final String URL = "jdbc:mysql://localhost:3306/book_storage";
    private static final int POOL_SIZE = 32;
    private static ConnectionPool instance;
    private static volatile boolean instanceIsCreated;

    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> surrenderedConnections;

    public static ConnectionPool getInstance() throws ConnectionDatabaseException {
        if (!instanceIsCreated) {
            synchronized (ConnectionPool.class) {
                if (!instanceIsCreated) {
                    instance = new ConnectionPool();
                    instanceIsCreated = true;
                }
            }
        }

        return instance;
    }

    private ConnectionPool() throws ConnectionDatabaseException {
        try {
            Class.forName(DRIVER_NAME);
            freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
            surrenderedConnections = new ArrayDeque<>(POOL_SIZE);

            for (int i = 0; i < POOL_SIZE; i++) {
                freeConnections.offer(new ProxyConnection(DriverManager.getConnection(URL, LOGIN, PASSWORD)));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ConnectionDatabaseException("Error while connection pool creating", e);
        }
    }

    public Connection getConnection() throws ConnectionDatabaseException {
        ProxyConnection connection;

        try {
            connection = freeConnections.take();
            surrenderedConnections.offer(connection);
        } catch (InterruptedException e) {
            throw new ConnectionDatabaseException("Error while getting connection", e);
        }

        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionDatabaseException {
        if (connection instanceof ProxyConnection) {
            surrenderedConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            throw new ConnectionDatabaseException("Invalid connection to close");
        }
    }

    public void destroyPool() throws ConnectionDatabaseException {
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                freeConnections.take().reallyClose();
            }
            deregisterDrivers();
        } catch (SQLException | InterruptedException e) {
            throw new ConnectionDatabaseException("Error while close connection pool", e);
        }
    }

    private void deregisterDrivers() throws SQLException {
        while (DriverManager.getDrivers().hasMoreElements()) {
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        }
    }
}
