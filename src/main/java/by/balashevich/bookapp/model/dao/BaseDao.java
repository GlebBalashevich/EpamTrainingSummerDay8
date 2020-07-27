package by.balashevich.bookapp.model.dao;

import by.balashevich.bookapp.model.connection.ConnectionPool;
import by.balashevich.bookapp.exception.ConnectionDatabaseException;
import by.balashevich.bookapp.exception.DaoApplicationException;
import by.balashevich.bookapp.model.entity.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<T extends Entity> {
    boolean add(T t) throws DaoApplicationException;

    boolean remove(T t) throws DaoApplicationException;

    T update(T t) throws DaoApplicationException;

    List<T> findAll(String ...sortTag) throws DaoApplicationException;

    T findById(long id) throws DaoApplicationException;

    default void close(Statement statement) throws DaoApplicationException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoApplicationException("Error while close statement", e);
            }
        }
    }

    default void close(Connection connection) throws DaoApplicationException {
        if (connection != null) {
            try {
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while close connection", e);
            }
        }
    }
}
