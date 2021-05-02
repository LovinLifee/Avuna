package net.avuna.event.impl.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.avuna.event.AbstractEvent;
import net.avuna.game.entity.IEntity;


@Getter
@AllArgsConstructor
public class EntityDamagedEvent<T extends IEntity> extends AbstractEvent {
	
	private T entity;
	private IEntity damagedBy;
	
	@Setter
	private int damage;
}
