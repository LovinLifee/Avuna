package net.avuna.game.entity.npc;

import lombok.extern.java.Log;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
public class NpcHandler {

    private Collection<INpc> npcs = Collections.emptyList();

    public void bind(Collection<INpc> npcs) {
        this.npcs = npcs;
    }

    public void bind(INpc[] npcs) {
        this.npcs = Arrays.asList(npcs);
    }

    public Collection<INpc> getNpcsById(int id) {
        return getNpcs().filter(n -> n.getNpcID() == id).collect(Collectors.toList());
    }

    public Collection<INpc> getNpcsByName(String name) {
        return getNpcs().filter(n -> n.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public int getNpcIdFromName(String name) {
        return npcLookupStrategy.idFromName(name);
    }

    public String getNpcNameFromId(int id) {
        return npcLookupStrategy.nameFromId(id);
    }

    public Stream<INpc> getNpcs() {
        if(npcs == Collections.EMPTY_LIST) {
            log.warning("You don't have a collection or array of npcs bound to NpcHandler");
        }
        return npcs.stream().filter(Objects::nonNull);
    }

    private NpcLookupStrategy npcLookupStrategy = new NpcLookupStrategy() {
        @Override
        public String nameFromId(int id) {
            INpc npc = getNpcs().filter(n -> n.getNpcID() == id).findAny().orElseThrow();
            return npc.getName();
        }
        @Override
        public int idFromName(String npcName) {
            INpc npc = getNpcs().filter(n -> n.getName().equalsIgnoreCase(npcName)).findAny().orElseThrow();
            return npc.getNpcID();
        }
    };

    public void setNpcLookupStrategy(NpcLookupStrategy strategy) {
        Objects.requireNonNull(strategy, "The NpcLookupStrategy cannot be null");
        this.npcLookupStrategy = strategy;
    }
}
