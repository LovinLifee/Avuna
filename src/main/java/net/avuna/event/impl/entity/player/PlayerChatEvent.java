package net.avuna.event.impl.entity.player;

import lombok.Getter;
import lombok.Setter;
import net.avuna.event.AbstractEvent;
import net.avuna.game.entity.player.IPlayer;

@Getter
@Setter
public class PlayerChatEvent<T extends IPlayer> extends AbstractEvent {
	
	private final T player;
	private String message;
	
	public PlayerChatEvent(T player, String message) {
		this.player = player;
		this.message = message;
	}
}
