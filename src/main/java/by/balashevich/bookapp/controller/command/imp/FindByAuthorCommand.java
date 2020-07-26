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

public class FindByAuthorCommand implements ActionCommand {
    private static final String AUTHOR = "author";

    @Override
    public Map<String, Object> execute(Map<String, String> actionParameters){
        BookServiceImpl bookService = new BookServiceImpl();
        Map<String, Object> executeResult = new HashMap<>();

        if (actionParameters.containsKey(AUTHOR)) {
            String author = actionParameters.get(AUTHOR);
            try {
                List<Book> findResult = bookService.findByAuthor(author);
                if (!findResult.isEmpty()) {
                    executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.SEARCH_RESULT.getPath());
                    executeResult.put(ResponseParameterType.BOOK_STORAGE.getName(), findResult);
                }
            } catch(ServiceApplicationException e){
                executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.ERROR.getPath());
                executeResult.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.APPERROR.getText() + e.getMessage());
            }
        }

        if (executeResult.isEmpty()) {
            executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.SEARCH_RESULT.getPath());
            executeResult.put(ResponseParameterType.MESSAGE.getName(), ResponseMessage.FINDEMPTY.getText());
        }

        return executeResult;
    }
}
