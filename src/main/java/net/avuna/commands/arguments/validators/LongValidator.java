package net.avuna.commands.arguments.validators;

import net.avuna.commands.arguments.ArgumentValidator;
import net.avuna.util.ExceptionUtils;

public class LongValidator implements ArgumentValidator<Long> {

    @Override
    public boolean validateType(String arg) {
        return !ExceptionUtils.doesThrow((() -> Long.parseLong(arg)), NumberFormatException.class);
    }

    @Override
    public Long get(String arg) {
        if(!validateType(arg)) {
            throw new IllegalArgumentException("Argument is not a valid long");
        }
        return Long.parseLong(arg);
    }
}
