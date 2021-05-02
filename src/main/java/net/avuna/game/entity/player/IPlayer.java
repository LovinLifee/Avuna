package net.avuna.game.entity.player;

import net.avuna.game.entity.IAttackableEntity;
import net.avuna.security.PlayerPermissions;

public interface IPlayer extends IAttackableEntity {

    public String getUsername();
    public void sendMessage(String message);
    public PlayerPermissions getPermissions();

    public default double getDropChanceModifier() {
        return 1.0D;
    }
}
