package by.balashevich.bookapp.connection;

import by.balashevich.bookapp.exception.ConnectionDatabaseException;
import com.mysql.cj.jdbc.MysqlDataSource;

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

    public static ConnectionPool getInstance(){
        if (!instanceIsCreated) {

            synchronized (ConnectionPool.class) {
                if (!instanceIsCreated) {
                    try {
                        instance = new ConnectionPool();
                    } catch (ConnectionDatabaseException e) {
                        System.out.println("Error while connection pool creating " + e); //there must be Logger
                    }
                    instanceIsCreated = true;
                }
            }
        }

        return instance;
    }

    ConnectionPool() throws ConnectionDatabaseException {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new ConnectionDatabaseException("Error while register jdbc driver", e);
        }
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        surrenderedConnections = new ArrayDeque<>(POOL_SIZE);

        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.offer(new ProxyConnection(DriverManager.getConnection(URL, LOGIN, PASSWORD)));
            } catch (SQLException e) {
                throw new ConnectionDatabaseException("Error while initialize connections", e);
            }
        }
    }

    public Connection getConnection() throws ConnectionDatabaseException {
        Connection connection;
        try {
            connection = freeConnections.take();
            surrenderedConnections.offer((ProxyConnection) connection);
        } catch (InterruptedException e) {  // TODO: 23.07.2020 is it ok to throw custom exception on interrupted
            throw new ConnectionDatabaseException("Error while getting connection", e);
        }

        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionDatabaseException { // FIXME: 23.07.2020 argument is ConnectionImpl
        if (connection.getClass().getSimpleName().equals("ProxyConnection")) {
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
        while(DriverManager.getDrivers().hasMoreElements()){
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            MysqlDataSource dataSource = new MysqlDataSource();
        }
    }
}
