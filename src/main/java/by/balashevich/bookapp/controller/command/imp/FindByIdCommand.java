package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.controller.command.ActionCommand;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseMessage;
import by.balashevich.bookapp.controller.command.ResponseParameterType;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.service.impl.BookServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FindByIdCommand implements ActionCommand {
    private static final String BOOK_ID = "bookId";

    @Override
    public Map<String, String> execute(Map<String, String> actionParameters) {
        BookServiceImpl bookService = new BookServiceImpl();
        Map<String, String> executeResult = new HashMap<>();

        if (actionParameters.containsKey(BOOK_ID)) {
            long bookId = Long.parseLong(actionParameters.get(BOOK_ID));
            Optional<Book> findResult = bookService.findById(bookId);
            if (findResult.isPresent()) {
                executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.ITEMCARD.getPath());
                executeResult.put(ResponseParameterType.BOOK_STORAGE.getName(), findResult.get().toString());
            }
        }

        if (executeResult.isEmpty()) {
            executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
            executeResult.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.FINDEMPTY.getText());
        }

        return executeResult;
    }
}
