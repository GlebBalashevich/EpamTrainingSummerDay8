package by.balashevich.bookapp.model.service.impl;

import by.balashevich.bookapp.dao.impl.BookListDaoImpl;
import by.balashevich.bookapp.exception.ServiceApplicationException;
import by.balashevich.bookapp.exception.DaoApplicationException;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;
import by.balashevich.bookapp.model.service.BookService;
import by.balashevich.bookapp.validator.BookValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    @Override
    public boolean addBook(Book book) throws ServiceApplicationException {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        boolean isBookAdded;

        try {
            isBookAdded = bookListDao.add(book);
        } catch (DaoApplicationException e) {
            throw new ServiceApplicationException("Error while adding book to storage", e);
        }

        return isBookAdded;
    }

    @Override
    public boolean removeBook(Book book) throws ServiceApplicationException {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        boolean isBookRemoved;

        try {
            isBookRemoved = bookListDao.remove(book);
        } catch (DaoApplicationException e) {
            throw new ServiceApplicationException("Error while removing book from storage", e);
        }

        return isBookRemoved;
    }

    @Override
    public Optional<Book> findById(long bookId) throws ServiceApplicationException {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        Optional<Book> targetBook = Optional.empty();
        try {
            targetBook = Optional.of(bookListDao.findById(bookId));
        } catch (DaoApplicationException e) {
            throw new ServiceApplicationException("Error while finding book by Id from storage", e);
        }

        return targetBook;
    }

    @Override
    public List<Book> findByTitle(String title) throws ServiceApplicationException {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        BookValidator bookValidator = new BookValidator();
        List<Book> targetBooks = new ArrayList<>();

        if (bookValidator.validateTitle(title)) {
            try {
                targetBooks = bookListDao.findByTitle(title);
            } catch (DaoApplicationException e) {
                throw new ServiceApplicationException("Error while finding book by Title from storage", e);
            }
        }

        return targetBooks;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        BookValidator bookValidator = new BookValidator();
        List<Book> targetBooks = new ArrayList<>();

        if (bookValidator.validateSingleAuthor(author)) {
            targetBooks = bookListDao.findByAuthor(author);
        }

        return targetBooks;
    }

    @Override
    public List<Book> findByYearPublication(int yearPublication) {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        BookValidator bookValidator = new BookValidator();
        List<Book> targetBooks = new ArrayList<>();

        if (bookValidator.validateYearPublication(yearPublication)) {
            targetBooks = bookListDao.findByYearPublication(yearPublication);
        }

        return targetBooks;
    }

    @Override
    public List<Book> findByLanguage(Language language) {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        List<Book> targetBooks = new ArrayList<>();

        if (language != null) {
            targetBooks = bookListDao.findByLanguage(language);
        }

        return targetBooks;
    }

    @Override
    public List<Book> sortById() {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        List<Book> sortedList = bookListDao.sortById();

        return sortedList;
    }

    @Override
    public List<Book> sortByTitle() {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        List<Book> sortedList = null;
        try {
            sortedList = bookListDao.findByTitle();
        } catch (DaoApplicationException e) {
            e.printStackTrace();
        }

        return sortedList;
    }

    @Override
    public List<Book> sortByAuthor() {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        List<Book> sortedList = bookListDao.sortByAuthor();

        return sortedList;
    }

    @Override
    public List<Book> sortByYearPublication() {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        List<Book> sortedList = bookListDao.sortByYearPublication();

        return sortedList;
    }

    @Override
    public List<Book> sortByLanguage() {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        List<Book> sortedList = bookListDao.sortByLanguage();

        return sortedList;
    }
}
