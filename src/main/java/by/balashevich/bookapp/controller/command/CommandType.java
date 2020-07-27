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
    SORT_BY_TAG(new SortByTagCommand()),
    EMPTY_COMMAND(new EmptyCommand());

    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand(){
        return command;
    }
}
