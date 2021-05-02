package net.avuna.commands.arguments.validators;

import net.avuna.commands.arguments.ArgumentValidator;

public class StringValidator implements ArgumentValidator<String> {

    @Override
    public boolean validateType(String arg) {
        return true;
    }

    @Override
    public String get(String arg) {
        return arg;
    }
}
