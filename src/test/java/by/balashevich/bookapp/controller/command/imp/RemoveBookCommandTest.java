package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.TestBookStorageData;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseMessage;
import by.balashevich.bookapp.controller.command.ResponseParameterType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class RemoveBookCommandTest {
    RemoveBookCommand removeBookCommand;

    @BeforeTest
    public void setUp() {
        removeBookCommand = new RemoveBookCommand();
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
    public void executeTestRemoved() {
        Map<String, String> request = new HashMap<>();
        request.put("bookParameters", "title : Good Signs; authors : T.Pratchett, N.Gaiman; yearPublication : 2010; language : ENGLISH");

        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
        expected.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.REMOVESUCCESSFULLY.getText());

        Map<String, Object> actual = removeBookCommand.execute(request);
        assertEquals(actual, expected);
    }

    @Test
    public void executeTestNotRemoved() {
        Map<String, String> request = new HashMap<>();
        request.put("book", "title : Master and Margarita; authors : M.Bulgakov; yearPublication : 1928; language : RUSSIAN");

        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
        expected.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.REMOVEUNSUCCESSFULLY.getText());

        Map<String, Object> actual = removeBookCommand.execute(request);
        assertEquals(actual, expected);
    }
}