package net.avuna.net.proxy.packets.impl;

import com.github.simplenet.Client;
import com.github.simplenet.packet.Packet;
import net.avuna.Avuna;
import net.avuna.game.items.drops.Drop;
import net.avuna.game.items.drops.DropTable;
import net.avuna.net.proxy.packets.PacketHandler;

public class NpcDropsPacket implements PacketHandler {

    @Override
    public void read(Client client) {
        client.readInt(npcId -> {
            Packet packet = Packet.builder();
            packet.putByte(3);
            packet.putInt(npcId);
            DropTable<Drop> drops = Avuna.getDropHandler().getDropTable(npcId);
            for(Drop d : drops.getDrops()) {
                packet.putInt(d.getItemId());
                packet.putInt(d.getMinAmount());
                packet.putInt(d.getMaxAmount());
                packet.putDouble(d.getChance());
            }
            packet.putByte(-1);
            packet.queueAndFlush(client);
        });
    }
}
