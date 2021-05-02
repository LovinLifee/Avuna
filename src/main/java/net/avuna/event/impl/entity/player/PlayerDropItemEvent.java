package net.avuna.event.impl.entity.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.event.AbstractEvent;
import net.avuna.game.entity.player.IPlayer;

import net.avuna.game.items.ItemStack;

@Getter
@RequiredArgsConstructor
public class PlayerDropItemEvent<T extends IPlayer> extends AbstractEvent {

	private final T player;
	private final ItemStack itemStack;
	private final int slot;
}
