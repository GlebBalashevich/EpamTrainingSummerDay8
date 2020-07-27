package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseParameterType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class EmptyCommandTest {
    EmptyCommand emptyCommand;

    @BeforeTest
    public void setUp() {
        emptyCommand = new EmptyCommand();
    }

    @Test
    public void executeTest() {
        Map<String, Object> expected = new HashMap<>();
        expected.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());

        Map<String, Object> actual = emptyCommand.execute(new HashMap<>());
        assertEquals(actual, expected);
    }
}