package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.controller.command.ActionCommand;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseMessage;
import by.balashevich.bookapp.controller.command.ResponseParameterType;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.service.impl.BookServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindByTitleCommand implements ActionCommand {
    private static final String TITLE = "title";

    @Override
    public Map<String, String> execute(Map<String, String> actionParameters) {
        BookServiceImpl bookService = new BookServiceImpl();
        Map<String, String> executeResult = new HashMap<>();

        if (actionParameters.containsKey(TITLE)) {
            String title = actionParameters.get(TITLE);
            List<Book> findResult = bookService.findByTitle(title);
            if (!findResult.isEmpty()) {
                executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.SEARCH_RESULT.getPath());
                executeResult.put(ResponseParameterType.BOOK_STORAGE.getName(), findResult.toString());
            }
        }

        if (executeResult.isEmpty()) {
            executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.SEARCH_RESULT.getPath());
            executeResult.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.FINDEMPTY.getText());
        }

        return executeResult;
    }
}
