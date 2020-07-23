package by.balashevich.bookapp.model.entity;

public enum Language {
    RUSSIAN("Russian"),
    ENGLISH("English"),
    GERMAN("German"),
    ITALIAN("Italian");

    private final String name;

    private Language(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
