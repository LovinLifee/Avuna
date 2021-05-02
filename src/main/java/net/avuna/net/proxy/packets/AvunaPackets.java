package net.avuna.net.proxy.packets;

import lombok.Getter;
import net.avuna.net.proxy.packets.impl.CommandPacket;
import net.avuna.net.proxy.packets.impl.PlayerCountPacket;

import java.util.HashMap;
import java.util.Map;

@Getter
public class AvunaPackets {

    private final Map<Integer, PacketHandler> packets = new HashMap<>();

    public AvunaPackets() {
        packets.put(1, new PlayerCountPacket());
        packets.put(2, new CommandPacket());
    }

    public void addPacket(int opcode, PacketHandler packetHandler) {
        if(packets.containsKey(opcode)) {
            throw new IllegalArgumentException(String.format("A packet for opcode %d already exists", opcode));
        } else {
            packets.put(opcode, packetHandler);
        }
    }
}
