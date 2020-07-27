package by.balashevich.bookapp.model.service.impl;

import by.balashevich.bookapp.exception.DaoApplicationException;
import by.balashevich.bookapp.exception.ServiceApplicationException;
import by.balashevich.bookapp.model.dao.impl.BookListDaoImpl;
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
        Optional<Book> targetBook;

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
    public List<Book> findByAuthor(String author) throws ServiceApplicationException {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        BookValidator bookValidator = new BookValidator();
        List<Book> targetBooks = new ArrayList<>();

        if (bookValidator.validateSingleAuthor(author)) {
            try {
                targetBooks = bookListDao.findByAuthor(author);
            } catch (DaoApplicationException e) {
                throw new ServiceApplicationException("Error while finding book by Author from storage", e);
            }
        }

        return targetBooks;
    }

    @Override
    public List<Book> findByYearPublication(int yearPublication) throws ServiceApplicationException {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        BookValidator bookValidator = new BookValidator();
        List<Book> targetBooks = new ArrayList<>();

        if (bookValidator.validateYearPublication(yearPublication)) {
            try {
                targetBooks = bookListDao.findByYearPublication(yearPublication);
            } catch (DaoApplicationException e) {
                throw new ServiceApplicationException("Error while finding book by Year publication from storage", e);
            }
        }

        return targetBooks;
    }

    @Override
    public List<Book> findByLanguage(Language language) throws ServiceApplicationException {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        List<Book> targetBooks = new ArrayList<>();

        if (language != null) {
            try {
                targetBooks = bookListDao.findByLanguage(language);
            } catch (DaoApplicationException e) {
                throw new ServiceApplicationException("Error while finding book by Language from storage", e);
            }
        }

        return targetBooks;
    }

    @Override
    public List<Book> sortByTag(String sortTag) throws ServiceApplicationException {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        List<Book> sortedList;

        try {
            sortedList = bookListDao.findAll(sortTag);
        } catch (DaoApplicationException e) {
            throw new ServiceApplicationException("Error while sorting books from storage", e);
        }

        return sortedList;
    }
}
