package net.avuna.event.impl.entity.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.game.Position;

@Getter
@AllArgsConstructor
public class PlayerMoveEvent<T extends IPlayer> {
	
	private final T player;
	private Position oldPosition; 
	private Position newPosition;
}
