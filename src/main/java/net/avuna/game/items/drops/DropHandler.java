package net.avuna.game.items.drops;

import lombok.Getter;
import net.avuna.Avuna;
import net.avuna.config.Configurable;
import net.avuna.event.impl.entity.npc.NpcDropItemEvent;
import net.avuna.game.entity.player.IPlayer;

import java.util.Collection;
import java.util.Collections;

public class DropHandler implements Configurable<DropConfig> {

    @Getter
    private final DropConfig config;

    public DropHandler(DropConfig config) {
        this.config = config;
    }

    public void addDrop(int npcId, Drop drop) {
        DropTable<Drop> drops = config.getNpcDrops().computeIfAbsent(npcId, k -> new DropTable<Drop>());
        drops.addDrop(drop);
    }

    public <T extends IPlayer> Collection<Drop> rollDropsForPlayer(T player, int npcId) {
        return rollDropsForPlayer(player, npcId, 1);
    }

    public <T extends IPlayer> Collection<Drop> rollDropsForPlayer(T player, int npcId, int rolls) {
        Collection<Drop> drops = config.getNpcDrops().get(npcId).rollDrops(rolls, player.getDropChanceModifier());
        NpcDropItemEvent<T> event = new NpcDropItemEvent<>(npcId, player, drops);
        Avuna.getEventManager().submit(event);
        if(event.isCancelled()) {
            return Collections.emptyList();
        } else {
            return drops;
        }
    }

    public DropTable<Drop> getDropTable(int npcId) {
        return config.getNpcDrops().get(npcId);
    }
}
