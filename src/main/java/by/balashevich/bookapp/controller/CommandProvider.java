package by.balashevich.bookapp.controller;

import by.balashevich.bookapp.controller.command.ActionCommand;
import by.balashevich.bookapp.controller.command.CommandType;

public class CommandProvider {

    public ActionCommand defineCommand(String command){
        ActionCommand definedCommand = null;

        if (command != null && !command.isBlank()){
            try{
                definedCommand = CommandType.valueOf(command.toUpperCase()).getCommand();
            } catch (IllegalArgumentException e){
                definedCommand = CommandType.EMPTY_COMMAND.getCommand();
            }
        }

        if(definedCommand == null){
            definedCommand = CommandType.EMPTY_COMMAND.getCommand();
        }

        return definedCommand;
    }
}
