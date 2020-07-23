package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.controller.command.ActionCommand;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseParameterType;
import by.balashevich.bookapp.model.entity.Book;
import by.balashevich.bookapp.model.service.impl.BookServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortByIdCommand implements ActionCommand {

    @Override
    public Map<String, String> execute(Map<String, String> actionParameters) {
        BookServiceImpl bookService = new BookServiceImpl();
        Map<String, String> executeResult = new HashMap<>();

        List<Book> sortResult = bookService.sortById();
        executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
        executeResult.put(ResponseParameterType.BOOK_STORAGE.getName(), sortResult.toString());

        return executeResult;
    }
}
