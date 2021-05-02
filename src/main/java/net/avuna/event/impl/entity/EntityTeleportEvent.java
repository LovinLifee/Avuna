package net.avuna.event.impl.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.avuna.event.AbstractEvent;
import net.avuna.game.entity.IEntity;

import net.avuna.game.Position;

@Getter
@AllArgsConstructor
public class EntityTeleportEvent<T extends IEntity> extends AbstractEvent {
	
	private final T entity;
	
	private Position oldPosition;
	
	@Setter
	private Position newPosition;
}
