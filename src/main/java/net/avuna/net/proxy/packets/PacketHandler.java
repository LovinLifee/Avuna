package net.avuna.net.proxy.packets;

import com.github.simplenet.Client;

@FunctionalInterface
public interface PacketHandler {
    public void read(Client client);
}
