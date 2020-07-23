package by.balashevich.bookapp.dao.impl;

import by.balashevich.bookapp.connection.ConnectionPool;
import by.balashevich.bookapp.dao.BookListDao;
import by.balashevich.bookapp.exception.ConnectionDatabaseException;
import by.balashevich.bookapp.exception.DaoApplicationException;
import by.balashevich.bookapp.model.creator.BookCreator;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;
import by.balashevich.bookapp.model.entity.BookStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BookListDaoImpl implements BookListDao {
    private static final String SQL_ADD_BOOK = "INSERT INTO book(title, authors, year_publication, language)" +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_REMOVE_BOOK = "DELETE FROM book " +
            "WHERE title = ? AND authors = ? AND year_publication = ? AND language = ?";
    private static final String SQL_FIND_ALL_BOOKS = "SELECT bookid, title, authors, year_publication, " +
            "language FROM book ORDER BY bookid";
    private static final String SQL_FIND_BOOK_BY_ID = "SELECT bookid, title, authors, year_publication, " +
            "language FROM book WHERE (bookid = ?) OR (bookid IS NULL)"; // FIXME: 22.07.2020 change offer
    private static final String SQL_FIND_BOOKS_BY_TITLE = "SELECT bookid, title, authors, year_publication, language FROM book WHERE (? IS NULL) OR (title = ?) ORDER BY title";
    private static final String BOOKID_COLUMN = "bookid";
    private static final String TITLE_COLUMN = "title";
    private static final String AUTHORS_COLUMN = "authors";
    private static final String YEAR_PUBLICATION_COLUMN = "year_publication";
    private static final String LANGUAGE_COLUMN = "language";

    @Override
    public boolean add(Book book) throws DaoApplicationException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        boolean isBookAdded;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_BOOK)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthors().toString());
            statement.setInt(3, book.getYearPublication());
            statement.setString(4, book.getLanguage().getName());
            int executeResult = statement.executeUpdate();
            isBookAdded = executeResult > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while adding book to storage", e);
        }

        return isBookAdded;
    }

    @Override
    public boolean remove(Book book) throws DaoApplicationException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        boolean isBookRemoved;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_BOOK)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthors().toString());
            statement.setInt(3, book.getYearPublication());
            statement.setString(4, book.getLanguage().getName());
            int executeResult = statement.executeUpdate();
            isBookRemoved = executeResult > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while removing book to storage", e);
        }

        return isBookRemoved;
    }

    @Override
    public Book update(Book book) {
        return null;
    } // TODO: 22.07.2020 thinking about method logic

    @Override
    public List<Book> findAll() throws DaoApplicationException {
        List<Book> targetBooks = new ArrayList<>();
        BookCreator bookCreator = new BookCreator();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_BOOKS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                long bookId = resultSet.getLong(BOOKID_COLUMN);
                String title = resultSet.getString(TITLE_COLUMN);
                String authorsData = resultSet.getString(AUTHORS_COLUMN);
                List<String> authors = bookCreator.createList(authorsData);
                int yearPublication = resultSet.getInt(YEAR_PUBLICATION_COLUMN);
                Language language = Language.valueOf(resultSet.getString(LANGUAGE_COLUMN));

                Book book = new Book(bookId, title, authors, yearPublication, language);
                targetBooks.add(book);
            }
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while finding all books from storage", e);
        }

        return targetBooks;
    }

    @Override
    public Book findById(Long findingBookId) throws DaoApplicationException {
        Book targetBook = null;
        BookCreator bookCreator = new BookCreator();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BOOK_BY_ID)) {

            statement.setLong(1, findingBookId);
            try (ResultSet resultSet = statement.executeQuery()) { // TODO: 22.07.2020 is syntax correct
                if (resultSet.next()) {
                    long bookId = resultSet.getLong(BOOKID_COLUMN);
                    String title = resultSet.getString(TITLE_COLUMN);
                    String authorsData = resultSet.getString(AUTHORS_COLUMN);
                    List<String> authors = bookCreator.createList(authorsData);
                    int yearPublication = resultSet.getInt(YEAR_PUBLICATION_COLUMN);
                    Language language = Language.valueOf(resultSet.getString(LANGUAGE_COLUMN));

                    targetBook = new Book(bookId, title, authors, yearPublication, language);
                }
            }
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while finding book by Id from storage", e);
        }

        return targetBook;
    }

    @Override
    public List<Book> findByTitle(String findingTitle) throws DaoApplicationException {
        List<Book> targetBooks = new ArrayList<>();
        BookCreator bookCreator = new BookCreator();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BOOKS_BY_TITLE)) {

            statement.setString(1, findingTitle);
            try (ResultSet resultSet = statement.executeQuery()) { // TODO: 22.07.2020 is syntax correct (try try)
                while (resultSet.next()) {
                    long bookId = resultSet.getLong(BOOKID_COLUMN);
                    String title = resultSet.getString(TITLE_COLUMN);
                    String authorsData = resultSet.getString(TITLE_COLUMN);
                    List<String> authors = bookCreator.createList(authorsData);
                    int yearPublication = resultSet.getInt(YEAR_PUBLICATION_COLUMN);
                    Language language = Language.valueOf(resultSet.getString(LANGUAGE_COLUMN));

                    Book book = new Book(bookId, title, authors, yearPublication, language);
                    targetBooks.add(book);
                }
            }
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoApplicationException("Error while finding books by title from storage", e);
        }

        return targetBooks;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> targetBooks = new ArrayList<>();

        return targetBooks;
    }

    @Override
    public List<Book> findByYearPublication(int yearPublication) {
        List<Book> targetBooks = new ArrayList<>();

        return targetBooks;
    }

    @Override
    public List<Book> findByLanguage(Language language) {
        List<Book> targetBooks = new ArrayList<>();

        return targetBooks;
    }

    @Override
    public List<Book> sortById() {
        List<Book> sortedBooks = new ArrayList<>();

        return sortedBooks;
    }

    @Override
    public List<Book> sortByTitle() throws DaoApplicationException {

        List<Book> sortedBooks = new ArrayList<>();

        return sortedBooks;
    }

    @Override
    public List<Book> sortByAuthor() {
        List<Book> sortedBooks = new ArrayList<>();

        return sortedBooks;
    }

    @Override
    public List<Book> sortByYearPublication() {
        List<Book> sortedBooks = new ArrayList<>();

        return sortedBooks;
    }

    @Override
    public List<Book> sortByLanguage() {
        List<Book> sortedBooks = new ArrayList<>();

        return sortedBooks;
    }
}
