package net.avuna.commands;

import lombok.Getter;

public class CommandNotFoundException extends Exception {

    @Getter
    private final String commandName;

    public CommandNotFoundException(String commandName) {
        super("The command " + commandName + " does not exist");
        this.commandName = commandName;
    }
}
