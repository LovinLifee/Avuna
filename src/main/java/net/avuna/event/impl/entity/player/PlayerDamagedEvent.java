package net.avuna.event.impl.entity.player;

import net.avuna.game.entity.IEntity;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.event.impl.entity.EntityDamagedEvent;

public class PlayerDamagedEvent<T extends IPlayer> extends EntityDamagedEvent<T> {

	public PlayerDamagedEvent(T entity, IEntity damagedBy, int damage) {
		super(entity, damagedBy, damage);
	}
}
