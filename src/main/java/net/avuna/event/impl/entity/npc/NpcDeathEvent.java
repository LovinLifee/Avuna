package net.avuna.event.impl.entity.npc;

import net.avuna.game.entity.IEntity;
import net.avuna.game.entity.npc.INpc;
import net.avuna.event.impl.entity.EntityDeathEvent;

public class NpcDeathEvent<T extends INpc> extends EntityDeathEvent<T> {

	public NpcDeathEvent(T entity, IEntity killedBy) {
		super(entity, killedBy);
	}
}
