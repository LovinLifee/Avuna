package net.avuna.event.net.rest;

import lombok.RequiredArgsConstructor;
import net.avuna.game.entity.player.AbstractPlayer;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class ConsolePlayer extends AbstractPlayer {

    private final Session session;

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
        return "Console";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsolePlayer that = (ConsolePlayer) o;
        return Objects.equals(session, that.session);
    }

    @Override
    public void sendMessage(String message) {
        try {
            session.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
