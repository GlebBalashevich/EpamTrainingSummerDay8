package by.balashevich.bookapp.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookStorage {
    private static final int MAX_CAPACITY = 50;
    private static BookStorage instance;

    private List<Book> books;

    private BookStorage() {
        books = new ArrayList<>();
    }

    public static BookStorage getInstance() {
        if (instance == null) {
            instance = new BookStorage();
        }

        return instance;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public boolean addBook(Book book) {
        boolean isAdded = false;

        if (!books.contains(book) && books.size() < MAX_CAPACITY) {
            isAdded = books.add(book);
        }

        return isAdded;
    }

    public boolean removeBook(Book book) {
        return books.remove(book);
    }
}
