package by.balashevich.bookapp.model.service.impl;

import by.balashevich.bookapp.TestBookStorageData;
import by.balashevich.bookapp.exception.ServiceApplicationException;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class BookServiceImplTest {
    BookServiceImpl bookService;

    @BeforeTest
    public void setUp() {
        bookService = new BookServiceImpl();
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
    public void addBookTest() throws ServiceApplicationException {
        Book addingBook = new Book("Best Served Cold",
                new ArrayList<>(Arrays.asList("J.Abercrombie")), 2009, Language.ENGLISH);

        assertTrue(bookService.addBook(addingBook));
    }

    @Test
    public void removeBookTest() throws ServiceApplicationException {
        Book removingBook = new Book("Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN);

        assertTrue(bookService.removeBook(removingBook));
    }

    @Test
    public void testFindById() throws ServiceApplicationException {
        Book findingBook = new Book(5, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH);
        Optional<Book> expected = Optional.of(findingBook);

        Optional<Book> actual = bookService.findById(5);
        assertEquals(actual, expected);
    }

    @Test
    public void findByTitleTest() throws ServiceApplicationException {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book(4, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        expected.add(new Book(5, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH));

        List<Book> actual = bookService.findByTitle("12 Cheers");
        assertEquals(actual, expected);
    }

    @Test
    public void findByTitleEmptyTest() throws ServiceApplicationException {
        List<Book> expected = new ArrayList<>();

        List<Book> actual = bookService.findByTitle(null);
        assertEquals(actual, expected);
    }

    @Test
    public void findByAuthorTest() throws ServiceApplicationException {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book(7, "Roadside Picnic",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1956, Language.RUSSIAN));
        expected.add(new Book(8, "Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN));

        List<Book> actual = bookService.findByAuthor("B.Strugatsky");
        assertEquals(actual, expected);
    }

    @Test
    public void findByAuthorEmptyTest() throws ServiceApplicationException {
        List<Book> expected = new ArrayList<>();

        List<Book> actual = bookService.findByAuthor("asdfghjkloiuytrewqasdfghjkloiunbvcxzxcvbvb");
        assertEquals(actual, expected);
    }

    @Test
    public void findByYearPublicationTest() throws ServiceApplicationException {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book(4, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        expected.add(new Book(10, "Process",
                new ArrayList<>(Arrays.asList("F.Kafka")), 1925, Language.GERMAN));

        List<Book> actual = bookService.findByYearPublication(1925);
        assertEquals(actual, expected);
    }

    @Test
    public void findByYearPublicationEmptyTest() throws ServiceApplicationException {
        List<Book> expected = new ArrayList<>();

        List<Book> actual = bookService.findByYearPublication(-1000);
        assertEquals(actual, expected);
    }

    @Test
    public void findByLanguageTest() throws ServiceApplicationException {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book(4, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        expected.add(new Book(7, "Roadside Picnic",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1956, Language.RUSSIAN));
        expected.add(new Book(8, "Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN));

        List<Book> actual = bookService.findByLanguage(Language.RUSSIAN);
        assertEquals(actual, expected);
    }

    @Test
    public void sortByTagTest() throws ServiceApplicationException {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book(4, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        expected.add(new Book(5, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH));
        expected.add(new Book(3, "1984",
                new ArrayList<>(Arrays.asList("J.Oruel")), 1949, Language.ENGLISH));
        expected.add(new Book(9, "Divine Comedy",
                new ArrayList<>(Arrays.asList("A.Dante")), 1265, Language.ITALIAN));
        expected.add(new Book(6, "Faust",
                new ArrayList<>(Arrays.asList("I.V.Goethe")), 1808, Language.GERMAN));
        expected.add(new Book(2, "Good Signs",
                new ArrayList<>(Arrays.asList("T.Pratchett", "N.Gaiman")), 2010, Language.ENGLISH));
        expected.add(new Book(8, "Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN));
        expected.add(new Book(10, "Process",
                new ArrayList<>(Arrays.asList("F.Kafka")), 1925, Language.GERMAN));
        expected.add(new Book(7, "Roadside Picnic",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1956, Language.RUSSIAN));
        expected.add(new Book(1, "The Lord Of The Rings",
                new ArrayList<>(Arrays.asList("J.R.R.Tolkien")), 1956, Language.ENGLISH));

        List<Book> actual = bookService.sortByTag("title");
        assertEquals(actual, expected);
    }
}