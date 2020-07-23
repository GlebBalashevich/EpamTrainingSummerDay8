package by.balashevich.bookapp.controller.command;

import by.balashevich.bookapp.controller.command.imp.*;

public enum CommandType {
    ADD_BOOK(new AddBookCommand()),
    REMOVE_BOOK(new RemoveBookCommand()),
    FIND_ID(new FindByIdCommand()),
    FIND_TITLE(new FindByTitleCommand()),
    FIND_AUTHOR(new FindByAuthorCommand()),
    FIND_YEAR_PUBLICATION(new FindByYearPublicationCommand()),
    FIND_LANGUAGE(new FindByLanguageCommand()),
    SORT_ID(new SortByIdCommand()),
    SORT_TITLE(new SortByTitleCommand()),
    SORT_AUTHOR(new SortByAuthorCommand()),
    SORT_YEAR_PUBLICATION(new SortByYearPublicationCommand()),
    SORT_LANGUAGE(new SortByLanguageCommand()),
    EMPTY_COMMAND(new EmptyCommand());

    private ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand(){
        return command;
    }
}
