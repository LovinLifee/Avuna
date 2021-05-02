package net.avuna.event.impl.entity.npc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import net.avuna.event.AbstractEvent;
import net.avuna.game.items.drops.Drop;
import net.avuna.game.entity.player.IPlayer;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class NpcDropItemEvent<P extends IPlayer> extends AbstractEvent {
    private final int npcId;
    private final P player;
    private final Collection<Drop> drops;
}
