package net.avuna.event.impl.entity.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import net.avuna.event.AbstractEvent;
import net.avuna.game.items.ItemStack;
import net.avuna.game.entity.player.IPlayer;

@Getter
@RequiredArgsConstructor
public class PlayerEquipItemEvent<T extends IPlayer> extends AbstractEvent {
    private final T player;
    private final ItemStack item;
    private final int itemSlot;
}
