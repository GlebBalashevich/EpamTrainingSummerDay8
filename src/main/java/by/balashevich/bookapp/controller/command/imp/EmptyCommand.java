package by.balashevich.bookapp.controller.command.imp;

import by.balashevich.bookapp.controller.command.ActionCommand;
import by.balashevich.bookapp.controller.command.PagePath;
import by.balashevich.bookapp.controller.command.ResponseParameterType;

import java.util.HashMap;
import java.util.Map;

public class EmptyCommand implements ActionCommand {

    @Override
    public Map<String, String> execute(Map<String, String> actionParameters) {
        Map<String, String> executeResult = new HashMap<>();
        executeResult.put(ResponseParameterType.PAGE.getName(), PagePath.MAIN.getPath());
        return executeResult;
    }
}
