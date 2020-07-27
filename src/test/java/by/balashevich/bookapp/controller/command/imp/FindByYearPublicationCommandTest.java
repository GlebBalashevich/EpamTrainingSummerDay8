package by.balashevich.bookapp.controller.command.imp;

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

import static org.testng.Assert.*;

public class FindByYearPublicationCommandTest {
    FindByYearPublicationCommand findByYearPublicationCommand;

    @BeforeTest
    public void setUp(){
        findByYearPublicationCommand = new FindByYearPublicationCommand();
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
        request.put("yearPublication", "1925");
        List<Book> expectedList = new ArrayList<>();
        expectedList.add(new Book(4,"12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        expectedList.add(new Book(10,"Process",
                new ArrayList<>(Arrays.asList("F.Kafka")), 1925, Language.GERMAN));

        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.SEARCH_RESULT.getPath());
        expected.put(ResponseParameterType.BOOK_STORAGE.getName(), expectedList);

        Map<String, Object> actual = findByYearPublicationCommand.execute(request);
        assertEquals(actual, expected);
    }

    @Test
    public void executeTestEmpty() {
        Map<String, String> request = new HashMap<>();
        request.put("title", "1925");

        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.SEARCH_RESULT.getPath());
        expected.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.FINDEMPTY.getText());

        Map<String, Object> actual = findByYearPublicationCommand.execute(request);
        assertEquals(actual, expected);
    }
}