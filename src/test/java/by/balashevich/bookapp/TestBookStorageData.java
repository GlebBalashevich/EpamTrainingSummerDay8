package by.balashevich.bookapp;

import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.entity.Language;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestBookStorageData {
    private static final String SQL_DELETE_BOOKS = "DELETE FROM book";
    private static final String SQL_RESET_PRIMARY_KEY = "ALTER TABLE book AUTO_INCREMENT = 1";
    private static final String SQL_ADD_BOOKS = "INSERT INTO book(title, authors, year_publication, language)" +
            "VALUES (?, ?, ?, ?)";
    private static List<Book> bookList;

    private TestBookStorageData() {
    }

    public static void resetBookStorage() throws ClassNotFoundException, SQLException {
        fillList();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_storage", "root", "Java1234");
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BOOKS);
        statement.executeUpdate();
        statement.execute(SQL_RESET_PRIMARY_KEY);
        statement = connection.prepareStatement(SQL_ADD_BOOKS);
        for (Book book : bookList) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthors().toString());
            statement.setInt(3, book.getYearPublication());
            statement.setString(4, book.getLanguage().getName());
            statement.executeUpdate();
        }
        statement.close();
        connection.close();
    }

    private static void fillList() {
        bookList = new ArrayList<>();

        bookList.add(new Book("The Lord Of The Rings",
                new ArrayList<>(Arrays.asList("J.R.R.Tolkien")), 1956, Language.ENGLISH));
        bookList.add(new Book("Good Signs",
                new ArrayList<>(Arrays.asList("T.Pratchett", "N.Gaiman")), 2010, Language.ENGLISH));
        bookList.add(new Book("1984",
                new ArrayList<>(Arrays.asList("J.Oruel")), 1949, Language.ENGLISH));
        bookList.add(new Book("12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1925, Language.RUSSIAN));
        bookList.add(new Book("12 Cheers",
                new ArrayList<>(Arrays.asList("I.Ilf", "E.Petrov")), 1940, Language.ENGLISH));
        bookList.add(new Book("Faust",
                new ArrayList<>(Arrays.asList("I.V.Goethe")), 1808, Language.GERMAN));
        bookList.add(new Book("Roadside Picnic",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1956, Language.RUSSIAN));
        bookList.add(new Book("Monday starts at Saturday",
                new ArrayList<>(Arrays.asList("A.Strugatsky", "B.Strugatsky")), 1950, Language.RUSSIAN));
        bookList.add(new Book("Divine Comedy",
                new ArrayList<>(Arrays.asList("A.Dante")), 1265, Language.ITALIAN));
        bookList.add(new Book("Process",
                new ArrayList<>(Arrays.asList("F.Kafka")), 1925, Language.GERMAN));
    }
}
