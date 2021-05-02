package net.avuna.event.impl.entity.player;

import net.avuna.game.entity.player.IPlayer;
import net.avuna.event.impl.entity.EntityTeleportEvent;
import net.avuna.game.Position;

public class PlayerTeleportEvent<T extends IPlayer> extends EntityTeleportEvent<T> {
	
	public PlayerTeleportEvent(T entity, Position oldPosition, Position newPosition) {
		super(entity, oldPosition, newPosition);
	}
}
