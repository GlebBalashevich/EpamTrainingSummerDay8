package by.balashevich.bookapp.dao.impl;

import by.balashevich.bookapp.TestBookStorageData;
import by.balashevich.bookapp.exception.DaoApplicationException;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class BookListDaoImplTest {
    BookListDaoImpl bookListDao;

    @BeforeTest
    public void setUp() {
        bookListDao = new BookListDaoImpl();
    }

    @BeforeMethod
    public void prepareData() {
        try {
            TestBookStorageData.resetBookStorage();
        } catch (SQLException | ClassNotFoundException e) {
            fail("Error with connection to test data base, test fail");
        }
    }

    @Test
    public void addTest() throws DaoApplicationException {
        Book addingBook = new Book("Best Served Cold",
                new ArrayList<>(Arrays.asList("J.Abercrombie")), 2009, Language.ENGLISH);

        assertTrue(bookListDao.add(addingBook));
    }

    @Test
    public void removeTest() throws DaoApplicationException {
        Book removingBook = new Book(8L, "Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN);

        assertTrue(bookListDao.remove(removingBook));
    }

    @Test
    public void updateTest() throws DaoApplicationException {
        Book updatingBook = new Book(8L, "Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1971, Language.ENGLISH);
        Book expected = new Book(8L, "Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN);

        Book actual = bookListDao.update(updatingBook);
        assertEquals(actual, expected);
    }

    @Test
    public void findAllTest() throws DaoApplicationException {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book(1,"The Lord Of The Rings",
                new ArrayList<>(Arrays.asList("J.R.R.Tolkien")), 1956, Language.ENGLISH));
        expected.add(new Book(2,"Good Signs",
                new ArrayList<>(Arrays.asList("T.Pratchett", "N.Gaiman")), 2010, Language.ENGLISH));
        expected.add(new Book(3, "1984",
                new ArrayList<>(Arrays.asList("J.Oruel")), 1949, Language.ENGLISH));
        expected.add(new Book(4,"12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        expected.add(new Book(5, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH));
        expected.add(new Book(6,"Faust",
                new ArrayList<>(Arrays.asList("I.V.Goethe")), 1808, Language.GERMAN));
        expected.add(new Book(7,"Roadside Picnic",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1956, Language.RUSSIAN));
        expected.add(new Book(8,"Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN));
        expected.add(new Book(9,"Divine Comedy",
                new ArrayList<>(Arrays.asList("A.Dante")), 1265, Language.ITALIAN));
        expected.add(new Book(10,"Process",
                new ArrayList<>(Arrays.asList("F.Kafka")), 1925, Language.GERMAN));

        List<Book> actual = bookListDao.findAll();
        assertEquals(actual, expected);
    }

    @Test
    public void testFindById() throws DaoApplicationException {
        Book expected = new Book(5, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH);

        Book actual = bookListDao.findById(5);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByTitle() throws DaoApplicationException {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book(4,"12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        expected.add(new Book(5, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH));

        List<Book> actual = bookListDao.findByTitle("12 Cheers");
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByAuthor() throws DaoApplicationException {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book(7,"Roadside Picnic",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1956, Language.RUSSIAN));
        expected.add(new Book(8,"Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN));

        List<Book> actual = bookListDao.findByAuthor("B.Strugatsky");
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByYearPublication() {
    }

    @Test
    public void testFindByLanguage() {
    }
}