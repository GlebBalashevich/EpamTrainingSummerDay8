package by.balashevich.bookapp.model.dao;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SortTagConverterTest {

    @Test
    public void convertSortTagTest() {
        String expected = "year_publication";
        String actual = SortTagConverter.convertTag(new String[]{"yearPublication"});

        assertEquals(actual, expected);
    }

    @Test
    public void convertSortTagDefaultValueTest() {
        String expected = "bookid";
        String actual = SortTagConverter.convertTag(new String[]{"1234"});

        assertEquals(actual, expected);
    }
}