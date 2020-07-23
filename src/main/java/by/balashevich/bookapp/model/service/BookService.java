package by.balashevich.bookapp.model.service;

import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;
import by.balashevich.bookapp.exception.ServiceApplicationException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    boolean addBook(Book book) throws ServiceApplicationException;

    boolean removeBook(Book book) throws ServiceApplicationException;

    Optional<Book> findById(long bookId);

    List<Book> findByTitle(String title) throws ServiceApplicationException;

    List<Book> findByAuthor(String author) throws ServiceApplicationException;

    List<Book> findByYearPublication(int yearPublication) throws ServiceApplicationException;

    List<Book> findByLanguage(Language language) throws ServiceApplicationException;

    List<Book> sortById() throws ServiceApplicationException;

    List<Book> sortByTitle() throws ServiceApplicationException;

    List<Book> sortByAuthor() throws ServiceApplicationException;

    List<Book> sortByYearPublication() throws ServiceApplicationException;

    List<Book> sortByLanguage() throws ServiceApplicationException;
}
