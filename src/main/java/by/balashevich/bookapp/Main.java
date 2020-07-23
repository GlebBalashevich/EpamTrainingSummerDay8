package by.balashevich.bookapp;

import by.balashevich.bookapp.connection.ConnectionPool;
import by.balashevich.bookapp.dao.impl.BookListDaoImpl;
import by.balashevich.bookapp.exception.ConnectionDatabaseException;
import by.balashevich.bookapp.exception.DaoApplicationException;
import by.balashevich.bookapp.model.creator.BookCreator;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws DaoApplicationException, ConnectionDatabaseException {
        BookListDaoImpl bookListDao = new BookListDaoImpl();
        Book book1 = new Book("The Lord Of The Rings",
                new ArrayList<>(Arrays.asList("J.R.R.Tolkien")), 1956, Language.ENGLISH);
        Book book2 = new Book("Good Signs",
                new ArrayList<>(Arrays.asList("T.Pratchett", "N.Gaiman")), 2010, Language.ENGLISH);
        Book book3 = new Book("1984",
                new ArrayList<>(Arrays.asList("J.Oruel")), 1949, Language.ENGLISH);
        Book book4 = new Book("12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN);
        Book book5 = new Book("12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH);
        Book book6 = new Book("Faust",
                new ArrayList<>(Arrays.asList("I.V.Goethe")), 1808, Language.GERMAN);
        Book book7 = new Book("Roadside Picnic",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1956, Language.RUSSIAN);
        Book book8 = new Book("Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN);
        Book book9 = new Book("Divine Comedy",
                new ArrayList<>(Arrays.asList("A.Dante")), 1265, Language.ITALIAN);
        Book book10 = new Book("Process",
                new ArrayList<>(Arrays.asList("F.Kafka")), 1925, Language.GERMAN);

        bookListDao.remove(book3);
        bookListDao.add(book3);



    }
}
