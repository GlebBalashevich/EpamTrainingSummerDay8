package by.balashevich.bookapp.controller;

import by.balashevich.bookapp.controller.command.ActionCommand;

import java.util.Map;

public class Controller {
    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public Map<String, Object> doBookAction(String commandType, Map<String, String> actionParameters) {
        CommandProvider command = new CommandProvider();
        ActionCommand actionCommand = command.defineCommand(commandType);
        Map<String, Object> actionResult = actionCommand.execute(actionParameters);

        return actionResult;
    }
}
