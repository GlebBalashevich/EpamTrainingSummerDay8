package by.balashevich.bookapp.model.creator;

import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;
import by.balashevich.bookapp.validator.BookValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookCreator {
    private static final String LINE_DELIMITER = ";";
    private static final String LIST_DELIMITER = ",";
    private static final String ELEMENT_DELIMITER = ":";
    private static final String LIST_BRACKET = "\\[?\\]?";

    public Optional<Book> createBook(String bookData) {
        BookValidator bookValidator = new BookValidator();
        Optional<Book> createdBook = Optional.empty();
        String[] bookElements = bookData.split(LINE_DELIMITER);
        long bookId = 0;
        String title = null;
        List<String> authors = null;
        int yearPublication = 0;
        Language language = null;

        for (String bookElement : bookElements) {
            String[] elementParts = bookElement.split(ELEMENT_DELIMITER);
            String fieldName = elementParts[0].trim();
            String fieldValue = elementParts[1].trim();

            if (fieldName.contains("bookId")) {
                bookId = Long.parseLong(fieldValue);
            }

            if (fieldName.contains("title")) {
                title = fieldValue;
            }
            if (fieldName.contains("authors")) {
                authors = createList(fieldValue);
            }
            if (fieldName.contains("yearPublication")) {
                yearPublication = Integer.parseInt(fieldValue);
            }
            if (fieldName.contains("language")) {
                language = Language.valueOf(fieldValue);
            }
        }

        if (bookValidator.validateBookElements(title, authors, yearPublication, language)) {
            createdBook = Optional.of(new Book(bookId, title, authors, yearPublication, language));
        }

        return createdBook;
    }

    public List<String> createList(String listData) {
        listData = listData.replaceAll(LIST_BRACKET, "");
        String[] elementsList = listData.split(LIST_DELIMITER);
        List<String> resultList = new ArrayList<>();

        for (String author : elementsList) {
            resultList.add(author.trim());
        }

        return resultList;
    }
}
