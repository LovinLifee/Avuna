package net.avuna.commands.arguments.validators;

import net.avuna.commands.arguments.ArgumentValidator;
import net.avuna.util.ExceptionUtils;

public class FloatValidator implements ArgumentValidator<Float> {

    @Override
    public boolean validateType(String arg) {
        return !ExceptionUtils.doesThrow((() -> Float.parseFloat(arg)), NumberFormatException.class);
    }

    @Override
    public Float get(String arg) {
        if(!validateType(arg)) {
            throw new IllegalArgumentException("Argument is not a valid float");
        }
        return Float.parseFloat(arg);
    }
}
