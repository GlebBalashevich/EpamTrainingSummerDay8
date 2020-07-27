package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.controller.command.ActionCommand;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseMessage;
import by.balashevich.bookapp.controller.command.ResponseParameterType;
import by.balashevich.bookapp.exception.ServiceApplicationException;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.service.impl.BookServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortByTagCommand implements ActionCommand {
    private static final String SORT_TAG_KEY = "sortTag";

    @Override
    public Map<String, Object> execute(Map<String, String> actionParameters) {
        BookServiceImpl bookService = new BookServiceImpl();
        Map<String, Object> executeResult = new HashMap<>();

        if (actionParameters.containsKey(SORT_TAG_KEY)) {
            String sortingTag = actionParameters.get(SORT_TAG_KEY);
            try {
                List<Book> sortResult = bookService.sortByTag(sortingTag);
                executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
                executeResult.put(ResponseParameterType.BOOK_STORAGE.getName(), sortResult);
            } catch (ServiceApplicationException e) {
                executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.ERROR.getPath());
                executeResult.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.APPERROR.getText() + e);
            }
        }

        return executeResult;
    }
}
