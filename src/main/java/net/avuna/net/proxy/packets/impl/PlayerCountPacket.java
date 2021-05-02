package net.avuna.net.proxy.packets.impl;

import com.github.simplenet.Client;
import com.github.simplenet.packet.Packet;
import net.avuna.Avuna;
import net.avuna.net.proxy.packets.PacketHandler;

public class PlayerCountPacket implements PacketHandler {

    @Override
    public void read(Client client) {
        long playerCount = Avuna.getPlayers().getPlayers().count();
        Packet packet = Packet.builder().putByte(1).putLong(playerCount);
        packet.queueAndFlush(client);
    }
}
