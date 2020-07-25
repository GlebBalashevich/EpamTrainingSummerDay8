package by.balashevich.bookapp.dao;

import by.balashevich.bookapp.exception.DaoApplicationException;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;

import java.util.List;

public interface BookListDao extends BaseDao<Long, Book> {

    List<Book> findByTitle(String ...title) throws DaoApplicationException;

    List<Book> findByAuthor(String author) throws DaoApplicationException;

    List<Book> findByYearPublication(int yearPublication) throws DaoApplicationException;

    List<Book> findByLanguage(Language language) throws DaoApplicationException;

    List<Book> sortById() throws DaoApplicationException;

    List<Book> sortByAuthor() throws DaoApplicationException;

    List<Book> sortByYearPublication() throws DaoApplicationException;

    List<Book> sortByLanguage() throws DaoApplicationException;
}
