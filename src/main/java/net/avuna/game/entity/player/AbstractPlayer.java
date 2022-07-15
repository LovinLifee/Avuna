package net.avuna.game.entity.player;

import lombok.Getter;
import lombok.Setter;
import net.avuna.misc.Elo;
import net.avuna.tasks.security.PlayerPermissions;

@Getter
@Setter
public abstract class AbstractPlayer implements IPlayer {

    private double dropChanceModifier = 1D;
    private final PlayerPermissions permissions = PlayerPermissions.of(1);
    private final Elo pkingElo = Elo.ofDefault();

    public abstract String getUsername();

    @Override
    public String getName() {
        return getUsername();
    }

    public abstract void sendMessage(String message);
}
