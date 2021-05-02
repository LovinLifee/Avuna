package net.avuna.net.proxy.packets.impl;

import com.github.simplenet.Client;
import com.github.simplenet.packet.Packet;
import net.avuna.Avuna;
import net.avuna.commands.CommandNotFoundException;
import net.avuna.game.entity.player.AbstractPlayer;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.net.proxy.packets.PacketHandler;
import net.avuna.security.InsufficientPermissionsException;
import net.avuna.security.PlayerPermissions;

import java.util.function.Consumer;

public class CommandPacket implements PacketHandler {

    private static final ThreadLocal<Client> CLIENT_THREAD_LOCAL = new ThreadLocal<>();

    public static Consumer<String> onMessageRecieved = (message) -> {
        if(CLIENT_THREAD_LOCAL.get() != null) {
            Packet packet = Packet.builder().putByte(2).putString(message);
            packet.queueAndFlush(CLIENT_THREAD_LOCAL.get());
        }
    };

    private static final IPlayer CONSOLE = new AbstractPlayer() {

        private final PlayerPermissions permissions = PlayerPermissions.of(PlayerPermissions.CONSOLE);

        @Override
        public int getX() {
            return 0;
        }

        @Override
        public int getY() {
            return 0;
        }

        @Override
        public String getUsername() {
            return "ADMIN";
        }

        @Override
        public void sendMessage(String message) {
            onMessageRecieved.accept(message);
        }

        @Override
        public PlayerPermissions getPermissions() {
            return permissions;
        }
    };

    @Override
    public void read(Client client) {
        CLIENT_THREAD_LOCAL.set(client);
        client.readString(s -> {
            try {
                Avuna.getCommandHandler().execute(CONSOLE, s);
            } catch (InsufficientPermissionsException | CommandNotFoundException e) {
                CONSOLE.sendMessage(e.getMessage());
            }
        });
    }
}
