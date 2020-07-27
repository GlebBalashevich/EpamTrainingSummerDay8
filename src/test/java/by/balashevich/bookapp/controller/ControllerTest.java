package by.balashevich.bookapp.controller;

import by.balashevich.bookapp.TestBookStorageData;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseMessage;
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

public class ControllerTest {
    Controller controller;

    @BeforeTest
    public void setUp() {
        controller = Controller.getInstance();
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
    public void doBookActionSortTest() {
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

        Map<String, Object> actual = controller.doBookAction("SORT_BY_TAG", request);
        assertEquals(actual, expected);
    }

    @Test
    public void doBookActionFindTest() {
        String title = "12 Cheers";
        Map<String, String> request = new HashMap<>();
        request.put("title", title);
        List<Book> expectedList = new ArrayList<>();
        expectedList.add(new Book(4, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        expectedList.add(new Book(5, "12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH));
        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.SEARCH_RESULT.getPath());
        expected.put(ResponseParameterType.BOOK_STORAGE.getName(), expectedList);

        Map<String, Object> actual = controller.doBookAction("FIND_TITLE", request);
        assertEquals(actual, expected);
    }

    @Test
    public void doBookActionAddTest() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("bookParameters", "title : Master and Margarita; authors : M.Bulgakov; yearPublication : 1928; language : RUSSIAN");

        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
        expected.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.ADDSUCCESSFULLY.getText());

        Map<String, Object> actual = controller.doBookAction("ADD_BOOK", request);
        assertEquals(actual, expected);
    }

    @Test
    public void doBookActionRemoveTest() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("bookParameters", "title : 1984; authors : J.Oruel; yearPublication : 1949; language : ENGLISH");

        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
        expected.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.REMOVESUCCESSFULLY.getText());

        Map<String, Object> actual = controller.doBookAction("REMOVE_BOOK", request);
        assertEquals(actual, expected);
    }
}