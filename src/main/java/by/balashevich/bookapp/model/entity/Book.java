package by.balashevich.bookapp.model.entity;

import java.util.List;

public class Book extends Entity {
    private long bookId;
    private String title;
    private List<String> authors;
    private int yearPublication;
    private Language language;

    public Book(long bookId, String title, List<String> authors, int yearPublication, Language language) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.yearPublication = yearPublication;
        this.language = language;
    }

    public Book(String title, List<String> authors, int yearPublication, Language language) {
        this.title = title;
        this.authors = authors;
        this.yearPublication = yearPublication;
        this.language = language;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public int getYearPublication() {
        return yearPublication;
    }

    public void setYearPublication(int yearPublication) {
        this.yearPublication = yearPublication;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;

        return bookId == book.bookId &&
                yearPublication == book.yearPublication &&
                title.equals(book.title) &&
                authors.equals(book.authors) &&
                language == book.language;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 17 * result + (int) bookId;
        result = 17 * result + (title == null ? 0 : title.hashCode());
        result = 17 * result + (authors == null ? 0 : authors.hashCode());
        result = 17 * result + yearPublication;
        result = 17 * result + (language == null ? 0 : language.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return String.format("Book{bookId: %d; title: %s; authors: %s; yearPublication: %d; language: %s}",
                bookId, title, authors.toString(), yearPublication, language.name());
    }
}
