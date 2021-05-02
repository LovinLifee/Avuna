package net.avuna.commands.arguments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.commands.arguments.validators.BooleanValidator;
import net.avuna.commands.arguments.validators.IntegerValidator;
import net.avuna.commands.arguments.validators.PlayerValidator;
import net.avuna.commands.arguments.validators.StringValidator;
import net.avuna.game.entity.player.IPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class ArgumentParser {
    
    private final String[] arguments;

    @Getter
    private static Map<Class<?>, ArgumentValidator<?>> validators = new HashMap<>();

    static {
        validators.put(String.class, new StringValidator());
        validators.put(Boolean.class, new BooleanValidator());
        validators.put(Integer.class, new IntegerValidator());
        validators.put(Long.class, new IntegerValidator());
        validators.put(Float.class, new IntegerValidator());
        validators.put(Double.class, new IntegerValidator());
        validators.put(IPlayer.class, new PlayerValidator());
    }

    public boolean ensureTypes(Class<?>... classes) {
        if(arguments.length != classes.length) {
            return false;
        }
        for(int i = 0; i < arguments.length; i++) {
            String arg = arguments[i];
            Class<?> clazz = classes[i];
            if(arg == null || clazz == null) {
                return false;
            }
            ArgumentValidator mapper = validators.get(clazz);
            if(mapper == null) {
                return false;
            }
            return mapper.validateType(arg);
        }
        return false;
    }

    public <T> T asType(int index, Class<T> type) {
        ArgumentValidator<T> validator = (ArgumentValidator<T>) validators.get(type);
        if(validator == null) {
            throw new NullPointerException("There is no validator for " + type.getName());
        }
        String arg = arguments[index];
        return (T) validator.get(arg);
    }

    public String getString(int index) {
        return arguments[index];
    }

    public int getInt(int index) {
       return asType(index, Integer.class);
    }

    public Optional<IPlayer> getPlayer(int index) {
        String arg = arguments[index];
        return (Optional<IPlayer>) validators.get(IPlayer.class).get(arg);
    }

    public long getLong(int index) {
        return asType(index, Long.class);
    }

    public boolean getBoolean(int index) {
        return asType(index, Boolean.class);
    }

    public float getFloat(int index) {
        return asType(index, Float.class);
    }

    public double getDouble(int index) {
        return asType(index, Double.class);
    }

    public int length() {
        return arguments.length;
    }

    @Override
    public String toString() {
        return String.join(" ", arguments);
    }
}
