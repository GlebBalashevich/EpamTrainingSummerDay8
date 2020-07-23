package by.balashevich.bookapp.controller.command;

public enum ResponseMessage {
    ADDSUCCESSFULLY("The book was successfully added to the storage"),
    ADDUNSUCCESSFULLY("The book was not added to the storage"),
    REMOVESUCCESSFULLY("The book was successfully removed from the storage"),
    REMOVEUNSUCCESSFULLY("The book was not removed from the storage"),
    FINDEMPTY("Nothing was found for this query"),
    APPERROR("An error occurred in the app:");

    private String text;

    ResponseMessage(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
