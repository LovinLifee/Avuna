package net.avuna.commands.arguments.validators;

import net.avuna.Avuna;
import net.avuna.commands.arguments.ArgumentValidator;
import net.avuna.game.entity.player.IPlayer;

import java.util.Optional;

public class PlayerValidator implements ArgumentValidator<Optional<IPlayer>> {
    
    @Override
    public boolean validateType(String arg) {
        return arg != null && arg.length() > 0 && arg.length() <= 12 &&
                arg.matches("^[a-zA-Z0-9\\s_]+$");
    }

    @Override
    public Optional<IPlayer> get(String arg) {
        if(!validateType(arg)) {
            throw new IllegalArgumentException("Argument is not a valid player name");
        }
        return Avuna.getPlayers().lookup(arg);
    }
}
