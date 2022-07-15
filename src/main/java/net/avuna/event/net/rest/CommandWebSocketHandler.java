package net.avuna.event.net.rest;

import net.avuna.Avuna;
import net.avuna.commands.CommandNotFoundException;
import net.avuna.tasks.security.InsufficientPermissionsException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class CommandWebSocketHandler {

    private ConsolePlayer CONSOLE;

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        this.CONSOLE = new ConsolePlayer(session);
        Avuna.getPlayers().getBackingCollection().add(CONSOLE);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        Avuna.getPlayers().getBackingCollection().remove(CONSOLE);
        this.CONSOLE = null;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try {
            Avuna.getCommandHandler().execute(CONSOLE, message);
        } catch (InsufficientPermissionsException e) {
            e.printStackTrace();
        } catch (CommandNotFoundException e) {
            e.printStackTrace();
        }
    }
}
