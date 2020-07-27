package by.balashevich.bookapp.validator;

import by.balashevich.bookapp.model.entity.Language;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class BookValidatorTest {
    BookValidator bookValidator;

    @BeforeTest
    public void setUp() {
        bookValidator = new BookValidator();
    }

    @DataProvider(name = "bookElementsDataPositive")
    public Object[][] createBookElementsDataPositive() {
        return new Object[][]{
                {"The Lord of The Rings", new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),
                        1954, Language.ENGLISH, true},
                {null, new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),
                        1954, Language.ENGLISH, false},
                {"The Lord of The Rings", null, 1954, Language.ENGLISH, false},
                {"The Lord of The Rings", new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),
                        -1, Language.ENGLISH, false},
                {"The Lord of The Rings", new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),
                        1954, null, false}
        };
    }

    @Test(dataProvider = "bookElementsDataPositive")
    public void validateBookElementsTestPositive(String title, List<String> authors, int yearPublication,
                                                 Language language, boolean expected) {
        boolean actual = bookValidator.validateBookElements(title, authors, yearPublication, language);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "bookElementsDataNegative")
    public Object[][] createBookElementsDataNegative() {
        return new Object[][]{
                {"The Lord of The Rings", new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),
                        1954, Language.ENGLISH, false},
                {"", new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),
                        1954, Language.ENGLISH, true},
                {"The Lord of The Rings", new ArrayList<>(), 1954, Language.ENGLISH, true},
                {"The Lord of The Rings", new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),
                        3030, Language.ENGLISH, true},
                {"The Lord of The Rings", new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),
                        1954, null, true}
        };
    }

    @Test(dataProvider = "bookElementsDataNegative")
    public void validateBookElementsTestNegative(String title, List<String> authors, int yearPublication,
                                                 Language language, boolean expected) {
        boolean actual = bookValidator.validateBookElements(title, authors, yearPublication, language);
        assertNotEquals(actual, expected);
    }

    @DataProvider(name = "titleDataPositive")
    public Object[][] createTitleDataPositive() {
        return new Object[][]{
                {"The Lord Of The Rings", true},
                {"1982", true},
                {"Hi-FI", true},
                {"", false},
                {null, false}
        };
    }

    @Test(dataProvider = "titleDataPositive")
    public void validateTitleTestPositive(String title, boolean expected) {
        boolean actual = bookValidator.validateTitle(title);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "titleDataNegative")
    public Object[][] createTitleDataNegative() {
        return new Object[][]{
                {"The Lord Of The Rings", false},
                {"!!!!04&", true},
                {"Hi.I'm", false},
                {"", true},
                {null, true}
        };
    }

    @Test(dataProvider = "titleDataNegative")
    public void validateTitleTestNegative(String title, boolean expected) {
        boolean actual = bookValidator.validateTitle(title);
        assertNotEquals(actual, expected);
    }

    @DataProvider(name = "authorsDataPositive")
    public Object[][] createAuthorsDataPositive() {
        return new Object[][]{
                {new ArrayList<>(Arrays.asList("J.R.R. Tolkien")), true},
                {new ArrayList<>(Arrays.asList("Alexey Pekhov", "Sergei Luk")), true},
                {new ArrayList<>(Arrays.asList("Ab", "Bc", "Cd", "De", "Fg")), false},
                {new ArrayList<>(Arrays.asList("1234", "Bc")), false},
                {new ArrayList<>(), false},
                {null, false}
        };
    }

    @Test(dataProvider = "authorsDataPositive", dependsOnMethods = "validateSingleAuthorTestPositive")
    public void validateAuthorsTestPositive(List<String> authors, boolean expected) {
        boolean actual = bookValidator.validateAuthors(authors);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "authorsDataNegative")
    public Object[][] createAuthorsDataNegative() {
        return new Object[][]{
                {new ArrayList<>(Arrays.asList("J.R.R. Tolkien")), false},
                {new ArrayList<>(Arrays.asList("Alexey Pekhov", "Sergei Luk")), false},
                {new ArrayList<>(Arrays.asList("Ab", "Bc", "Cd", "De", "Fg")), true},
                {new ArrayList<>(Arrays.asList("1234", "Bc", "!@#")), true},
                {new ArrayList<>(), true},
                {null, true}
        };
    }

    @Test(dataProvider = "authorsDataNegative", dependsOnMethods = "validateSingleAuthorTestNegative")
    public void validateAuthorsTestNegative(List<String> authors, boolean expected) {
        boolean actual = bookValidator.validateAuthors(authors);
        assertNotEquals(actual, expected);
    }

    @DataProvider(name = "authorDataPositive")
    public Object[][] createAuthorDataPositive() {
        return new Object[][]{
                {"J.R.R. Tolkien", true},
                {"1234", false},
                {"", false},
                {null, false}
        };
    }

    @Test(dataProvider = "authorDataPositive")
    public void validateSingleAuthorTestPositive(String author, boolean expected) {
        boolean actual = bookValidator.validateSingleAuthor(author);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "authorDataNegative")
    public Object[][] createAuthorDataNegative() {
        return new Object[][]{
                {"J.R.R. Tolkien", false},
                {"1234", true},
                {"", true},
                {"^#$", true},
                {null, true},
        };
    }

    @Test(dataProvider = "authorDataNegative")
    public void validateSingleAuthorTestNegative(String author, boolean expected) {
        boolean actual = bookValidator.validateSingleAuthor(author);
        assertNotEquals(actual, expected);
    }

    @DataProvider(name = "yearPublicationDataPositive")
    public Object[][] createYearPublicationDataPositive() {
        return new Object[][]{
                {2020, true},
                {1936, true},
                {-1, false},
                {3010, false}
        };
    }

    @Test(dataProvider = "yearPublicationDataPositive")
    public void validateYearPublicationTestPositive(int yearPublication, boolean expected) {
        boolean actual = bookValidator.validateYearPublication(yearPublication);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "yearPublicationDataNegative")
    public Object[][] createYearPublicationDataNegative() {
        return new Object[][]{
                {Year.now().getValue(), false},
                {1936, false},
                {-1, true},
                {3010, true}
        };
    }

    @Test(dataProvider = "yearPublicationDataNegative")
    public void validateYearPublicationTestNegative(int yearPublication, boolean expected) {
        boolean actual = bookValidator.validateYearPublication(yearPublication);
        assertNotEquals(actual, expected);
    }
}