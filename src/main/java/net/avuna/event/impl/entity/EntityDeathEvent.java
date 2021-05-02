package net.avuna.event.impl.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.avuna.event.AbstractEvent;
import net.avuna.game.entity.IEntity;


@Getter
@AllArgsConstructor
public class EntityDeathEvent<T extends IEntity> extends AbstractEvent {

	private T entity;
	private IEntity killedBy;
}
