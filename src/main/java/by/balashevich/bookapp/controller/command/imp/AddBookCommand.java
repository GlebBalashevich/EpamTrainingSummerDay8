package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.controller.command.ActionCommand;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseMessage;
import by.balashevich.bookapp.controller.command.ResponseParameterType;
import by.balashevich.bookapp.exception.ServiceApplicationException;
import by.balashevich.bookapp.model.creator.BookCreator;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.service.impl.BookServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddBookCommand implements ActionCommand {
    private static final String BOOK_PARAMS = "bookParams";

    @Override
    public Map<String, Object> execute(Map<String, String> actionParameters) {
        BookServiceImpl bookService = new BookServiceImpl();
        BookCreator bookCreator = new BookCreator();
        Map<String, Object> executeResult = new HashMap<>();

        if (actionParameters.containsKey(BOOK_PARAMS)) {
            Optional<Book> transferredBook = bookCreator.createBook(actionParameters.get(BOOK_PARAMS));
            if (transferredBook.isPresent()) {
                try {
                    Book addingBook = transferredBook.get();
                    if (bookService.addBook(addingBook)) {
                        executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
                        executeResult.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.ADDSUCCESSFULLY.getText());
                    }
                } catch (ServiceApplicationException e) {
                    executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.ERROR.getPath());
                    executeResult.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.APPERROR.getText() + e.getMessage());
                }
            }
        }

        if (executeResult.isEmpty()) {
            executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
            executeResult.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.ADDUNSUCCESSFULLY.getText());
        }

        return executeResult;
    }
}
