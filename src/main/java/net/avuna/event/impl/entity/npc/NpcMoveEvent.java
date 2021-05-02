package net.avuna.event.impl.entity.npc;

import net.avuna.game.entity.npc.INpc;
import net.avuna.event.impl.entity.EntityMoveEvent;
import net.avuna.game.Position;

public class NpcMoveEvent<T extends INpc> extends EntityMoveEvent<T> {

	public NpcMoveEvent(T entity, Position oldPosition, Position newPosition) {
		super(entity, oldPosition, newPosition);
	}
}
