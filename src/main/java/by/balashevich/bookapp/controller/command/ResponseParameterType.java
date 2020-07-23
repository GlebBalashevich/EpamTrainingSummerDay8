package by.balashevich.bookapp.controller.command;

public enum ResponseParameterType {
    PAGE("page"),
    BOOK_STORAGE("bookStorage"),
    MESSAGE("message");

    private final String name;

    ResponseParameterType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
