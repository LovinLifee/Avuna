package net.avuna.commands.arguments.validators;

import net.avuna.commands.arguments.ArgumentValidator;
import net.avuna.util.ExceptionUtils;

public class DoubleValidator implements ArgumentValidator<Double> {

    @Override
    public boolean validateType(String arg) {
        return ExceptionUtils.doesThrow(() -> Double.parseDouble(arg), NumberFormatException.class);
    }

    @Override
    public Double get(String arg) {
        if(!validateType(arg)) {
            throw new IllegalArgumentException("Argument is not a valid double");
        }
        return Double.parseDouble(arg);
    }
}
