package by.balashevich.bookapp.controller.command;

public enum PagePath {
    MAIN("/jsp/main.jsp"),
    SEARCH_RESULT("/jsp/searchresult.jsp"),
    ERROR("/jsp/error.jsp"),
    ITEMCARD("/jsp/itemcard.jsp");

    private String path;

    PagePath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
