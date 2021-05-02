package net.avuna.tests;

import net.avuna.Avuna;
import net.avuna.commands.AbstractCommand;
import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.security.PlayerPermissions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TestProxyServer {

    private static final int PORT = 43595;

    public static void main(String[] args) {
        Avuna.bootstrap();
        Avuna.getCommandHandler().registerCommand(new AbstractCommand<IPlayer>() {
            @Override
            public void execute(IPlayer sender, String commandName, ArgumentParser parser) {
                sender.sendMessage("pong");
            }

            @Override
            public String commandName() {
                return "ping";
            }

            @Override
            public String description() {
                return "description";
            }
            @Override
            public long permissions() {
                return PlayerPermissions.CONSOLE;
            }
        });
        Avuna.getProxyServer().start();
        try {
            Socket socket = new Socket("0.0.0.0", PORT);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out.writeUTF("foo");
            out.writeUTF("bar");
            out.write(2); //2 is the send command packet opcode
            out.writeUTF("ping"); //this is the command you want to send
            byte opcode = in.readByte();
            String message = in.readUTF();
            System.out.println(message);
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Avuna.getProxyServer().stop();
    }
}
