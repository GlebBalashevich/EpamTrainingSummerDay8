package by.balashevich.bookapp.model.creator;

import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class BookCreatorTest {
    BookCreator bookCreator;

    @BeforeTest
    public void setUp(){
        bookCreator = new BookCreator();
    }

    @Test
    public void createBookTestPositive() {
        String bookData = "title : The Lord of The Rings; authors : J.R.R. Tolkien, Bilbo Baggins; yearPublication : 1956; language : ENGLISH";
        Optional<Book> expected = Optional.of(new Book("The Lord of The Rings",
                new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),1956, Language.ENGLISH));
        Optional<Book> actual = bookCreator.createBook(bookData);
        actual.get().setBookId(expected.get().getBookId());
        assertEquals(actual, expected);
    }

    @Test
    public void createBookTestNegative() {
        String bookData = "title : The Lord of The Rings; yearPublication : 1956; language : ENGLISH";
        Optional<Book> expected = Optional.of(new Book("The Lord of The Rings",
                new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins")),1956, Language.ENGLISH));
        Optional<Book> actual = bookCreator.createBook(bookData);
        assertNotEquals(actual, expected);
    }

    @Test
    public void createListTestPositive() {
        String authorsData = "J.R.R. Tolkien, Bilbo Baggins";
        List<String> expected = new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins"));
        List<String> actual = bookCreator.createList(authorsData);
        assertEquals(actual, expected);
    }

    @Test
    public void createListTestNegative() {
        String authorsData = "J.R.R. Tolkien Bilbo Baggins";
        List<String> expected = new ArrayList<>(Arrays.asList("J.R.R. Tolkien", "Bilbo Baggins"));
        List<String> actual = bookCreator.createList(authorsData);
        assertNotEquals(actual, expected);
    }
}