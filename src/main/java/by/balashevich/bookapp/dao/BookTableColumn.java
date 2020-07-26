package by.balashevich.bookapp.dao;

public enum BookTableColumn {
    BOOKID("bookid"),
    TITLE("title"),
    AUTHORS("authors"),
    YEAR_PUBLICATION("year_publication"),
    LANGUAGE("language");

    private final String label;

    BookTableColumn(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

}
