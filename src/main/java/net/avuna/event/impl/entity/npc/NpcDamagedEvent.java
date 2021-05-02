package net.avuna.event.impl.entity.npc;

import net.avuna.game.entity.IEntity;
import net.avuna.game.entity.npc.INpc;
import net.avuna.event.impl.entity.EntityDamagedEvent;

public class NpcDamagedEvent<T extends INpc> extends EntityDamagedEvent<T> {

	public NpcDamagedEvent(T entity, IEntity damagedBy, int damage) {
		super(entity, damagedBy, damage);
	}
}
