package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.TestBookStorageData;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseMessage;
import by.balashevich.bookapp.controller.command.ResponseParameterType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.*;

import static org.testng.Assert.*;

public class AddBookCommandTest {
    AddBookCommand addBookCommand;

    @BeforeTest
    public void setUp() {
        addBookCommand = new AddBookCommand();
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
    public void executeTestAdded() {
        Map<String, String> request = new HashMap<>();
        request.put("bookParameters", "title : Master and Margarita; authors : M.Bulgakov; yearPublication : 1928; language : RUSSIAN");

        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
        expected.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.ADDSUCCESSFULLY.getText());

        Map<String, Object> actual = addBookCommand.execute(request);
        assertEquals(actual, expected);
    }

    @Test
    public void executeTestNotAdded() {
        Map<String, String> request = new HashMap<>();
        request.put("book", "title : Master and Margarita; authors : M.Bulgakov; yearPublication : 1928; language : RUSSIAN");

        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
        expected.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.ADDUNSUCCESSFULLY.getText());

        Map<String, Object> actual = addBookCommand.execute(request);
        assertEquals(actual, expected);
    }
}