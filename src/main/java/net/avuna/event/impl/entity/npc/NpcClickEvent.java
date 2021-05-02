package net.avuna.event.impl.entity.npc;

import lombok.Getter;
import net.avuna.event.AbstractEvent;
import net.avuna.game.entity.npc.INpc;
import net.avuna.game.entity.player.IPlayer;

import net.avuna.game.ClickOption;

@Getter
public class NpcClickEvent<P extends IPlayer, N extends INpc> extends AbstractEvent {

	private final P player;
	private final N npc;
	private final ClickOption clickOption;
	
	public NpcClickEvent(P player, N npc, ClickOption clickOption) {
		this.player = player;
		this.npc = npc;
		this.clickOption = clickOption;
	}
}
