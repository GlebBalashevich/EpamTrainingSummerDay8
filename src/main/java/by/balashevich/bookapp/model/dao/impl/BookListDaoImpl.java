package by.balashevich.bookapp.model.dao.impl;

import by.balashevich.bookapp.model.connection.ConnectionPool;
import by.balashevich.bookapp.model.dao.BookListDao;
import by.balashevich.bookapp.model.dao.BookTableColumn;
import by.balashevich.bookapp.model.dao.SortTagConverter;
import by.balashevich.bookapp.exception.ConnectionDatabaseException;
import by.balashevich.bookapp.exception.DaoApplicationException;
import by.balashevich.bookapp.model.creator.BookCreator;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookListDaoImpl implements BookListDao {
    private static final String MULTIPLE_SYMBOLS = "%";
    private static final String SQL_ADD_BOOK = "INSERT INTO book(title, authors, year_publication, language)" +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_REMOVE_BOOK = "DELETE FROM book " +
            "WHERE title = ? AND authors = ? AND year_publication = ? AND language = ?";
    private static final String SQL_FIND_ALL_BOOKS = "SELECT bookid, title, authors, year_publication, " +
            "language FROM book";
    private static final String SQL_UPDATE_BOOK = "UPDATE book SET title = ?, authors = ?, " +
            "year_publication = ?, language = ? WHERE bookid = ?";
    private static final String SQL_FIND_BOOK_BY_ID = SQL_FIND_ALL_BOOKS + " WHERE bookid = ?";
    private static final String SQL_FIND_BOOKS_BY_TITLE = SQL_FIND_ALL_BOOKS + " WHERE title = ?";
    private static final String SQL_FIND_BOOKS_BY_AUTHOR = SQL_FIND_ALL_BOOKS + " WHERE authors LIKE ?";
    private static final String SQL_FIND_BOOKS_BY_YEAR_PUBLICATION = SQL_FIND_ALL_BOOKS + " WHERE year_publication = ?";
    private static final String SQL_FIND_BOOKS_BY_LANGUAGE = SQL_FIND_ALL_BOOKS + " WHERE language = ?";
    private static final String SQL_SORT_QUERY_PREFIX = " ORDER BY ";

    @Override
    public boolean add(Book book) throws DaoApplicationException {
        boolean isBookAdded;

        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_ADD_BOOK)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthors().toString());
                statement.setInt(3, book.getYearPublication());
                statement.setString(4, book.getLanguage().getName());
                isBookAdded = statement.executeUpdate() > 0;
            } catch (SQLException | ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while adding book to storage", e);
            }
        } catch (ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while adding book to storage", e);
        }

        return isBookAdded;
    }

    @Override
    public boolean remove(Book book) throws DaoApplicationException {
        boolean isBookRemoved;

        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_BOOK)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthors().toString());
                statement.setInt(3, book.getYearPublication());
                statement.setString(4, book.getLanguage().getName());
                isBookRemoved = statement.executeUpdate() > 0;
            } catch (SQLException | ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while removing book to storage", e);
            }
        } catch (ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while removing book to storage", e);
        }

        return isBookRemoved;
    }

    @Override
    public Book update(Book book) throws DaoApplicationException {
        Book updatedBook = findById(book.getBookId());

        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BOOK)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthors().toString());
                statement.setInt(3, book.getYearPublication());
                statement.setString(4, book.getLanguage().getName());
                statement.setLong(5, book.getBookId());
                int updateResult = statement.executeUpdate();
                if (updateResult == 0) {
                    throw new DaoApplicationException("Error while updating book, book is not exist in storage");
                }
            } catch (SQLException | ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while updating book in storage", e);
            }
        } catch (ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while updating book in storage", e);
        }

        return updatedBook;
    }

    @Override
    public List<Book> findAll(String... sortTag) throws DaoApplicationException {
        List<Book> targetBooks = new ArrayList<>();

        String sqlQuery = SQL_FIND_ALL_BOOKS;

        if (sortTag.length > 0) {
            StringBuilder stringBuilder = new StringBuilder(sqlQuery);
            stringBuilder.append(SQL_SORT_QUERY_PREFIX);
            stringBuilder.append(SortTagConverter.convertTag(sortTag));
            sqlQuery = stringBuilder.toString();
        }

        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlQuery);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Book book = createBookFromSql(resultSet);
                    targetBooks.add(book);
                }
            } catch (SQLException | ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while finding all books from storage", e);
            }
        } catch (ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while finding all books from storage", e);
        }

        return targetBooks;
    }

    @Override
    public Book findById(long findingBookId) throws DaoApplicationException {
        Book targetBook = null;

        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_FIND_BOOK_BY_ID)) {
                statement.setLong(1, findingBookId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        targetBook = createBookFromSql(resultSet);
                    }
                }
            } catch (SQLException | ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while finding book by Id from storage", e);
            }
        } catch (ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while finding book by Id from storage", e);
        }

        return targetBook;
    }

    @Override
    public List<Book> findByTitle(String title) throws DaoApplicationException {
        List<Book> targetBooks = new ArrayList<>();

        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_FIND_BOOKS_BY_TITLE)) {
                statement.setString(1, title);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Book book = createBookFromSql(resultSet);
                        targetBooks.add(book);
                    }
                }
            } catch (SQLException | ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while finding books by Title from storage", e);
            }
        } catch (ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while finding books by Title from storage", e);
        }

        return targetBooks;
    }

    @Override
    public List<Book> findByAuthor(String author) throws DaoApplicationException {
        List<Book> targetBooks = new ArrayList<>();

        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_FIND_BOOKS_BY_AUTHOR)) {
                statement.setString(1, MULTIPLE_SYMBOLS + author + MULTIPLE_SYMBOLS);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Book book = createBookFromSql(resultSet);
                        targetBooks.add(book);
                    }
                }
            } catch (SQLException | ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while finding books by Author from storage", e);
            }
        } catch (ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while finding books by Author from storage", e);
        }

        return targetBooks;
    }

    @Override
    public List<Book> findByYearPublication(int yearPublication) throws DaoApplicationException {
        List<Book> targetBooks = new ArrayList<>();

        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_FIND_BOOKS_BY_YEAR_PUBLICATION)) {
                statement.setInt(1, yearPublication);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Book book = createBookFromSql(resultSet);
                        targetBooks.add(book);
                    }
                }
            } catch (SQLException | ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while finding books by Year publication from storage", e);
            }
        } catch (ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while finding books by Year publication from storage", e);
        }

        return targetBooks;
    }

    @Override
    public List<Book> findByLanguage(Language language) throws DaoApplicationException {
        List<Book> targetBooks = new ArrayList<>();

        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_FIND_BOOKS_BY_LANGUAGE)) {
                statement.setString(1, language.getName());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Book book = createBookFromSql(resultSet);
                        targetBooks.add(book);
                    }
                }
            } catch (SQLException | ConnectionDatabaseException e) {
                throw new DaoApplicationException("Error while finding books by Language from storage", e);
            }
        } catch (ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while finding books by Language from storage", e);
        }

        return targetBooks;
    }

    public Book createBookFromSql (ResultSet resultSet) throws SQLException {
        BookCreator bookCreator = new BookCreator();
        long bookId = resultSet.getLong(BookTableColumn.BOOKID.getLabel());
        String title = resultSet.getString(BookTableColumn.TITLE.getLabel());
        String authorsData = resultSet.getString(BookTableColumn.AUTHORS.getLabel());
        List<String> authors = bookCreator.createList(authorsData);
        int yearPublication = resultSet.getInt(BookTableColumn.YEAR_PUBLICATION.getLabel());
        Language language = Language.valueOf(resultSet.getString(BookTableColumn.LANGUAGE.getLabel()));

        Book book = new Book(bookId, title, authors, yearPublication, language);

        return book;
    }
}
