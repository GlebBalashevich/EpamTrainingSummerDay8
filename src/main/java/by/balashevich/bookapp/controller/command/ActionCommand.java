package by.balashevich.bookapp.controller.command;

import java.util.Map;

public interface ActionCommand {
    Map<String, String> execute(Map<String, String> actionParameters);
}
