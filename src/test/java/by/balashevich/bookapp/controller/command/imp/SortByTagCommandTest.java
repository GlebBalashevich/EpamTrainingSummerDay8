package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.TestBookStorageData;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseParameterType;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class SortByTagCommandTest {
    SortByTagCommand sortByTagCommand;

    @BeforeTest
    public void setUp() {
        sortByTagCommand = new SortByTagCommand();
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
    public void executeTestPositive() {
        Map<String, String> request = new HashMap<>();
        request.put("sortTag", "title");
        List<Book> expectedList = new ArrayList<>();
        expectedList.add(new Book(4, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        expectedList.add(new Book(5, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH));
        expectedList.add(new Book(3, "1984",
                new ArrayList<>(Arrays.asList("J.Oruel")), 1949, Language.ENGLISH));
        expectedList.add(new Book(9, "Divine Comedy",
                new ArrayList<>(Arrays.asList("A.Dante")), 1265, Language.ITALIAN));
        expectedList.add(new Book(6, "Faust",
                new ArrayList<>(Arrays.asList("I.V.Goethe")), 1808, Language.GERMAN));
        expectedList.add(new Book(2, "Good Signs",
                new ArrayList<>(Arrays.asList("T.Pratchett", "N.Gaiman")), 2010, Language.ENGLISH));
        expectedList.add(new Book(8, "Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN));
        expectedList.add(new Book(10, "Process",
                new ArrayList<>(Arrays.asList("F.Kafka")), 1925, Language.GERMAN));
        expectedList.add(new Book(7, "Roadside Picnic",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1956, Language.RUSSIAN));
        expectedList.add(new Book(1, "The Lord Of The Rings",
                new ArrayList<>(Arrays.asList("J.R.R.Tolkien")), 1956, Language.ENGLISH));

        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
        expected.put(ResponseParameterType.BOOK_STORAGE.getName(), expectedList);

        Map<String, Object> actual = sortByTagCommand.execute(request);
        assertEquals(actual, expected);
    }
}