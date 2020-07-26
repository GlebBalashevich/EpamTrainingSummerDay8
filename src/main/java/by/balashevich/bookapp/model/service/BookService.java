package by.balashevich.bookapp.model.service;

import by.balashevich.bookapp.exception.ServiceApplicationException;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;

import java.util.List;
import java.util.Optional;

public interface BookService {
    boolean addBook(Book book) throws ServiceApplicationException;

    boolean removeBook(Book book) throws ServiceApplicationException;

    Optional<Book> findById(long bookId) throws ServiceApplicationException;

    List<Book> findByTitle(String title) throws ServiceApplicationException;

    List<Book> findByAuthor(String author) throws ServiceApplicationException;

    List<Book> findByYearPublication(int yearPublication) throws ServiceApplicationException;

    List<Book> findByLanguage(Language language) throws ServiceApplicationException;

    List<Book> sortByTag(String sortTag) throws ServiceApplicationException;

}
