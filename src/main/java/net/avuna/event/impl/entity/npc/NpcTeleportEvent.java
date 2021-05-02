package net.avuna.event.impl.entity.npc;
import net.avuna.game.entity.npc.INpc;
import net.avuna.event.impl.entity.EntityTeleportEvent;
import net.avuna.game.Position;

public class NpcTeleportEvent<T extends INpc> extends EntityTeleportEvent<T> {
	
	public NpcTeleportEvent(T entity, Position oldPosition, Position newPosition) {
		super(entity, oldPosition, newPosition);
	}
}
