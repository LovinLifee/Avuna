package net.avuna.game.entity.npc;

public interface NpcLookupStrategy {
    public int idFromName(String npcName);
    public String nameFromId(int id);
}
