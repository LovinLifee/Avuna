package net.avuna.game.entity.player;

import net.avuna.game.entity.IAttackableEntity;
import net.avuna.tasks.security.PlayerPermissions;

public interface IPlayer extends IAttackableEntity {

    public String getUsername();
    public void sendMessage(String message);
    public PlayerPermissions getPermissions();

    public default void sendMessage(String message, String... args) {
        sendMessage(String.format(message, (Object) args));
    }

    public default double getDropChanceModifier() {
        return 1.0D;
    }

    public default void save() {

    }
}
