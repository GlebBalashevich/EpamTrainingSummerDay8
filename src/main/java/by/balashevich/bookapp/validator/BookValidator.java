package by.balashevich.bookapp.validator;

import by.balashevich.bookapp.model.entity.Language;

import java.time.Year;
import java.util.List;

public class BookValidator {
    private static final int MAX_TITLE_LENGTH = 255;
    private static final int MAX_AUTHOR_LENGTH = 40;
    private static final int MAX_AUTHORS_NUMBER = 4;
    private static final String TITLE_CHARACTER = "[\\w\\p{Space}\\p{Pd}\\.']+";
    private static final String AUTHOR_CHARACTER = "[\\p{L}\\p{Space}\\.]+";

    public boolean validateBookElements(String title, List<String> authors, int yearPublication, Language language) {
        return validateTitle(title) &&
                validateAuthors(authors) &&
                validateYearPublication(yearPublication) && language != null;
    }

    public boolean validateTitle(String title) {
        boolean isValid = false;

        if (title != null && !title.isBlank()) {
            if (title.length() > 0 && title.length() <= MAX_TITLE_LENGTH) {
                isValid = title.matches(TITLE_CHARACTER);
            }
        }

        return isValid;
    }

    public boolean validateAuthors(List<String> authors) {
        boolean isValid = false;

        if (authors != null && !authors.isEmpty()) {
            if (authors.size() < MAX_AUTHORS_NUMBER) {
                int correctAuthorsIndex = 0;

                for (String author : authors) {
                    if (validateSingleAuthor(author)) {
                        correctAuthorsIndex++;
                    }
                }

                isValid = correctAuthorsIndex == authors.size();
            }
        }

        return isValid;
    }

    public boolean validateSingleAuthor(String author) {
        boolean isValid = false;

        if (author != null && !author.isEmpty()) {
            if (author.length() < MAX_AUTHOR_LENGTH) {
                isValid = author.matches(AUTHOR_CHARACTER);
            }
        }

        return isValid;
    }

    public boolean validateYearPublication(int yearPublication) {

        return (yearPublication > 0) && (yearPublication <= Year.now().getValue());
    }
}
